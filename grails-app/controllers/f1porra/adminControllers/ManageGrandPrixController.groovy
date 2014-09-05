package f1porra.adminControllers

class ManageGrandPrixController {

    def retrieveGPsInfoService
    def adminF1Service
    def retrieveResultsInfoService
    def clasificationService

    def index() {
        render (view: "manageGPs" , model : [])
    }


    def addSeasongGPsFromURL()
    {
        def url = params.url

        retrieveGPsInfoService.addGPsInformationFromURL(url)

        request.message = "Success"
        render (view: "manageGPs" , model : [])
    }


    def processResults()
    {
        def url = params.urlResults

        retrieveResultsInfoService.retrieveResults(url)

        request.message = "Success"
        render (view: "manageGPs" , model : [])
    }



    def processClassificationPointsPerUser()
    {

        int actualYear = new Date().getYear() + 1900
        clasificationService.processClasificationPerUserInSeason(actualYear)

        request.message = "Parece que ok"
        render (view: "manageGPs" , model : [])
    }


}
