package f1porra

import f1porra.f1.Driver
import f1porra.f1.DriverHierarchy
import f1porra.f1.GrandPrix
import grails.transaction.Transactional
import org.apache.commons.lang.StringUtils

@Transactional
class RetrieveResultsInfoService {

    DriverHierarchy getDriverHierarchy (String htmlData)
    {
        def iteratorPositions = htmlData.indexOf("table class=\"raceResults\"")
        iteratorPositions = htmlData.indexOf("</tr>",iteratorPositions)+5

        DriverHierarchy driverHierarchy = new DriverHierarchy()
        def allFound = 1

        for (def i=0; i<10; i++)
        {
            def positionOfDriver = htmlData.indexOf("href=" , iteratorPositions)

            positionOfDriver = htmlData.indexOf("\">",positionOfDriver) + 2
            def driverName = new String(htmlData[positionOfDriver .. htmlData.indexOf("</a>", positionOfDriver)-1])

            if (driverName.indexOf("  ") >= 0)
            {
                driverName = driverName[0 .. driverName.indexOf("  ")] + driverName[driverName.indexOf("  ") + 1 .. driverName.size()-1]
            }

            Driver driverResult = Driver.findByName(driverName)


            if (driverResult == null)
            {

                driverName = driverName[driverName.indexOf(" ") + 2 .. driverName.size()-1]

                driverResult = Driver.findByNameLike(driverName)

                if (driverResult == null)
                {
                    log.error driverName + " -> Piloto no encontrado. Imposible procesar resultados"
                    break;
                    allFound = 0
                }
            }


            if (i==0)
            {
                driverHierarchy.pos1 = driverResult
            }
            else if (i==1)
            {
                driverHierarchy.pos2 = driverResult
            }
            else if (i==2)
            {
                driverHierarchy.pos3 = driverResult
            }
            else if (i==3)
            {
                driverHierarchy.pos4 = driverResult
            }
            else if (i==4)
            {
                driverHierarchy.pos5 = driverResult
            }
            else if (i==5)
            {
                driverHierarchy.pos6 = driverResult
            }
            else if (i==6)
            {
                driverHierarchy.pos7 = driverResult
            }
            else if (i==7)
            {
                driverHierarchy.pos8 = driverResult
            }
            else if (i==8)
            {
                driverHierarchy.pos9 = driverResult
            }
            else if (i==9)
            {
                driverHierarchy.pos10 = driverResult
            }

            iteratorPositions = htmlData.indexOf("</tr>", iteratorPositions) + 5
        }
        if (allFound == 1)
            return driverHierarchy
        else
            return null
    }




    def processQualyResults(String baseResultsURL, GrandPrix gpOfResults)
    {

        Wget httpGetter = new Wget();
        def htmlData = httpGetter.get(baseResultsURL);

        def positionOfQualyURL = htmlData.indexOf("PRACTICE 3</a></li>")
        positionOfQualyURL = htmlData.indexOf("href=\"", positionOfQualyURL) + 6

        def urlOfQualy = new String(htmlData[positionOfQualyURL .. htmlData.indexOf("\">", positionOfQualyURL)-1])
        def baseURL = getBaseURL(baseResultsURL)

        if (urlOfQualy[0] == '/')
            urlOfQualy = new String(baseURL[0 .. baseURL.size()-2] + urlOfQualy)
        else
            urlOfQualy = new String (baseURL + urlOfQualy)

        httpGetter = new Wget();
        htmlData = httpGetter.get(urlOfQualy);

        DriverHierarchy driverHierarchy = getDriverHierarchy(htmlData)


        if (driverHierarchy != null)
        {
            if (gpOfResults.qualyResult != null)
            {
                def oldQualyResults = gpOfResults.qualyResult
                gpOfResults.qualyResult = null
                oldQualyResults.delete(flush:true)
            }

            if (driverHierarchy.save(flush: true) == null)
            {
                log.error "Error salvando resultados: " + gpOfResults.errors
                return null
            }
            gpOfResults.qualyResult = driverHierarchy

            if (gpOfResults.save(flush:true) == null)
            {
                log.error "Error salvando resultados: " + gpOfResults.errors
            }

        }

        return null
    }

    def processRaceResults(String baseResultsURL, GrandPrix gpOfResults)
    {

        Wget httpGetter = new Wget();
        def htmlData = httpGetter.get(baseResultsURL);

        DriverHierarchy driverHierarchy = getDriverHierarchy(htmlData)

        if (driverHierarchy != null)
        {
            if (gpOfResults.raceResult != null)
            {
                def oldQualyResults = gpOfResults.raceResult
                gpOfResults.raceResult = null
                oldQualyResults.delete(flush:true)
            }

            if (driverHierarchy.save(flush: true) == null)
            {
                log.error "Error salvando resultados: " + gpOfResults.errors
                return null
            }
            gpOfResults.raceResult = driverHierarchy

            if (gpOfResults.save(flush:true) == null)
            {
                log.error "Error salvando resultados: " + gpOfResults.errors
            }
        }

        return null
    }

    def processFastLapResults(String baseResultsURL, GrandPrix gpOfResults)
    {
        Wget httpGetter = new Wget();
        def htmlData = httpGetter.get(baseResultsURL);

        def positionOfFastLapURL = htmlData.indexOf(">RESULTS</a></li>")
        positionOfFastLapURL = htmlData.indexOf("href=\"", positionOfFastLapURL) + 6

        def urlOfFastLap = new String(htmlData[positionOfFastLapURL .. htmlData.indexOf("\">", positionOfFastLapURL)-1])
        def baseURL = getBaseURL(baseResultsURL)

        if (urlOfFastLap[0] == '/')
            urlOfFastLap = new String(baseURL[0 .. baseURL.size()-2] + urlOfFastLap)
        else
            urlOfFastLap = new String (baseURL + urlOfFastLap)

        httpGetter = new Wget();
        htmlData = httpGetter.get(urlOfFastLap);

        DriverHierarchy driverHierarchy = getDriverHierarchy(htmlData)

        gpOfResults.fastLap = driverHierarchy.pos1

        gpOfResults.save(flush: true)

        return null
    }



    def processResults(String url, int numberOfRaceInSeason)
    {
        //Wget httpGetter = new Wget();

        //def htmlData = httpGetter.get(url);
        def season = new Date().getYear() + 1900


        GrandPrix gpOfResult = GrandPrix.findBySeasonAndRaceNumberInSeason(season, numberOfRaceInSeason)

        if (gpOfResult == null)
        {
            log.error "Impossible process results. GP not found."
            return null
        }

        Date now = new Date()

        if (gpOfResult.qualyStart < (new Date(now.getTime() - 7200000)))
        {
            processQualyResults(url, gpOfResult)
        }

        if (gpOfResult.raceStart < (new Date(now.getTime() - 7200000)))
        {
            processRaceResults(url, gpOfResult)
            processFastLapResults(url, gpOfResult)
        }

        return null

    }







    String getBaseURL (String url)
    {
        def lastPositionOfBaseUrl = url.indexOf("/",7)
        String baseURL = new String(url[0 .. lastPositionOfBaseUrl])
        return baseURL
    }

    def getNumberOfRaces(String htmlData)
    {
        return StringUtils.countMatches(htmlData, "<td><a href=\"/results/season")
    }


    def retrieveResults(String url) {

        Wget httpGetter = new Wget();

        def htmlData = httpGetter.get(url);

        def baseURL = getBaseURL(url)

        def iteratorResultsPosition = htmlData.indexOf("table class=\"raceResults\"")
        def numberOfResults = getNumberOfRaces(htmlData)

        iteratorResultsPosition = htmlData.indexOf("</tr>", iteratorResultsPosition) + 5

        for (def i=0;i<numberOfResults;i++)
        {
            def urlOfResults = new String(htmlData[htmlData.indexOf("href=\"", iteratorResultsPosition)+6 .. htmlData.indexOf("\">", iteratorResultsPosition)-1])


            if (urlOfResults[0] == '/')
                urlOfResults = new String(baseURL[0 .. baseURL.size()-2] + urlOfResults)
            else
                urlOfResults = new String (baseURL + urlOfResults)

            processResults(urlOfResults, i+1)
            iteratorResultsPosition = htmlData.indexOf("</tr>", iteratorResultsPosition) + 5
        }

    }
}
