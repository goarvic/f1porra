package f1porra.adminControllers

import f1porra.f1.Driver
import f1porra.f1.Team

class DriversController {

    def retrieveDriversInfoService

    def index() {

        def drivers = Driver.list(sort:"name", order:"asc")

        render(view: "manageDrivers")
    }

    def driverAddForm()
    {

        def teams = Team.list(sort:"name", order:"asc")

        render(view: "createNewDriver", model : [teams : teams])
    }


    def processDriversAndTeams()
    {
        def url = params.urlTeamsAndDrivers

        retrieveDriversInfoService.retrieveInfoDrivers(url)

        redirect(controller: "drivers", action: "index")
    }
}
