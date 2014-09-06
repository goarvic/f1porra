package f1porra.adminControllers

import f1porra.ConfigParams

class ManageGrandPrixController {

    def retrieveGPsInfoService
    def adminF1Service
    def retrieveResultsInfoService
    def clasificationService

    def index() {

        ConfigParams configParams = null

        if (ConfigParams.count() == 0)
            request.error = "No encontrados parámetros de configuración"
        else
            configParams = ConfigParams.first()

        render (view: "manageGPs" , model : [configParams:configParams])
    }


    def addSeasongGPsFromURL()
    {
        def url = params.url

        retrieveGPsInfoService.addGPsInformationFromURL(url)

        flash.message = "Success"
        redirect(controller: "manageGrandPrix", action: "index")
    }


    def processResults()
    {
        def url = params.urlResults
        retrieveResultsInfoService.retrieveResults(url)

        flash.message = "Success"
        redirect(controller: "manageGrandPrix", action: "index")
    }


    def processClassificationPointsPerUser()
    {
        int actualYear = new Date().getYear() + 1900
        clasificationService.processClasificationPerUserInSeason(actualYear)

        flash.message = "Parece que ok"
        redirect(controller: "manageGrandPrix", action: "index")
    }


}
