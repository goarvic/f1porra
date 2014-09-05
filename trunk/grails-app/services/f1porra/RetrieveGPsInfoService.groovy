package f1porra

import f1porra.f1.GrandPrix
import grails.transaction.Transactional
import org.apache.commons.lang.StringUtils

import java.text.SimpleDateFormat

@Transactional
class RetrieveGPsInfoService {

    def adminF1Service

    def getCircuitName(String htmlData)
    {
        def positionOfCircuitName = htmlData.indexOf("Circuit Name")

        String circuitName

/*        if (positionOfCircuitName >= 0)
        {
            positionOfCircuitName = htmlData.indexOf("<td>", positionOfCircuitName) + 4
            circuitName = new String(htmlData[positionOfCircuitName .. htmlData.indexOf("</td>",positionOfCircuitName)-1])
        }
        else
        {*/
            positionOfCircuitName = htmlData.indexOf("raceResultsHeading")
            positionOfCircuitName = htmlData.indexOf("<h2>", positionOfCircuitName) + 4
            circuitName = new String(htmlData[positionOfCircuitName .. htmlData.indexOf("</h2>",positionOfCircuitName)-1])
        //}
        return circuitName
    }


    Date getFreePracticeStart(String htmlData)
    {
        def positionOfDate = htmlData.indexOf("CT_Time_1_1")



        positionOfDate = htmlData.indexOf("class=\"2", positionOfDate) + 7
        SimpleDateFormat dateFormatFromFile = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmXXX")

        Date dateToReturn = dateFormatFromFile.parse(new String(htmlData[positionOfDate .. htmlData.indexOf("\">", positionOfDate)]))

        return dateToReturn
    }

    Date getQualyStart (String htmlData)
    {
        def positionOfDate = htmlData.indexOf("CT_Time_2_3")

        positionOfDate = htmlData.indexOf("class=\"2", positionOfDate) + 7
        SimpleDateFormat dateFormatFromFile = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmXXX")

        Date dateToReturn = dateFormatFromFile.parse(new String(htmlData[positionOfDate .. htmlData.indexOf("\">", positionOfDate)]))

        return dateToReturn
    }

    Date getRaceStart(String htmlData)
    {
        def positionOfDate = htmlData.indexOf("CT_Time_3_1")

        positionOfDate = htmlData.indexOf("class=\"2", positionOfDate) + 7
        SimpleDateFormat dateFormatFromFile = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmXXX")

        Date dateToReturn = dateFormatFromFile.parse(new String(htmlData[positionOfDate .. htmlData.indexOf("\">", positionOfDate)]))

        return dateToReturn
    }


    GrandPrix getGPDetailsFromURL(String url)
    {
        Wget httpGetter = new Wget();
        def htmlData = httpGetter.get(url);

        GrandPrix gpDetails = new GrandPrix()
        gpDetails.name = getCircuitName(htmlData)
        gpDetails.freePracticeStart = getFreePracticeStart(htmlData)
        gpDetails.qualyStart = getQualyStart(htmlData)
        gpDetails.raceStart = getRaceStart(htmlData)

        return gpDetails
    }






    int getNumberOfRaces(String htmlData)
    {
        return StringUtils.countMatches(htmlData, "raceLocation")
    }


    String getBaseURL (String url)
    {
        def lastPositionOfBaseUrl = url.indexOf("/",7)
        String baseURL = new String(url[0 .. lastPositionOfBaseUrl])
        return baseURL
    }





    def addGPsInformationFromURL (String url)
    {
        Wget httpGetter = new Wget();

        def htmlData = httpGetter.get(url);
        def numberOfRaces = getNumberOfRaces(htmlData)
        def baseURL = getBaseURL(url)
        //def season = new String(htmlData[htmlData.indexOf("navRACES_")+9 .. htmlData.indexOf("navRACES_")+13])

        //log.info season

        def season = new Date().getYear()+1900

        log.info "---------- " + season

        //adminF1Service.removeF1GPsSeason(season)


        def iteratorCursorRaces = 0

        for (def i=0; i<numberOfRaces; i++)
        {
            def positionOfRaceURL
            positionOfRaceURL = htmlData.indexOf("raceNumber", iteratorCursorRaces)
            positionOfRaceURL = htmlData.indexOf("href=", positionOfRaceURL)

            def urlOfRace = new String(htmlData[positionOfRaceURL+6 .. htmlData.indexOf("\"", positionOfRaceURL+6)-1])

            if (urlOfRace[0] == '/')
            {
                urlOfRace = new String(baseURL[0 .. baseURL.size()-2] + urlOfRace)
            }
            else
                urlOfRace = new String (baseURL + urlOfRace)

            GrandPrix gpDetails = getGPDetailsFromURL(urlOfRace)
            gpDetails.raceNumberInSeason = i+1
            gpDetails.season
            if (gpDetails.save(flush:true)== null)
            {
                gpDetails.getErrors().each{error->
                    if (error.getFieldError().getField().equals("season"))
                        log.info "Esta carrera ya estaba almacenada en el sistema. Se obvia el guardado."
                    else
                    {
                        log.info error.getFieldError()
                        log.error "Error salvando Gran Premio" + gpDetails.errors
                    }

                }
            }
            else
            {
                //Tenemos que ver si los jobs se deben programar, porque habrá GP's que ya hayan sido
                Date now = new Date()
                if (gpDetails.qualyStart > now)
                {
                    Date dateToProgramJob = new Date(gpDetails.qualyStart.getTime() + 7200000)
                    UpdateResultsForGPJob.schedule(dateToProgramJob, [gpId : gpDetails.id])

                    dateToProgramJob = new Date(gpDetails.raceStart.getTime() + 7200000)
                    UpdateResultsForGPJob.schedule(dateToProgramJob,  [gpId : gpDetails.id])

                    dateToProgramJob = new Date(gpDetails.raceStart.getTime() + 14400000)
                    UpdateResultsForGPJob.schedule(dateToProgramJob, [gpId : gpDetails.id])

                    dateToProgramJob = new Date(gpDetails.raceStart.getTime() + 86400000)
                    UpdateResultsForGPJob.schedule(dateToProgramJob, [gpId : gpDetails.id])
                }
                else if (gpDetails.raceStart > now)
                {
                    Date dateToProgramJob = new Date(gpDetails.raceStart.getTime() + 7200000)
                    UpdateResultsForGPJob.schedule(dateToProgramJob,  [gpId : gpDetails.id])

                    dateToProgramJob = new Date(gpDetails.raceStart.getTime() + 14400000)
                    UpdateResultsForGPJob.schedule(dateToProgramJob, [gpId : gpDetails.id])

                    dateToProgramJob = new Date(gpDetails.raceStart.getTime() + 86400000)
                    UpdateResultsForGPJob.schedule(dateToProgramJob, [gpId : gpDetails.id])
                }

            }


            iteratorCursorRaces = positionOfRaceURL
        }





    }
}
