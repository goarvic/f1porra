package f1porra.userControllers

import f1porra.User
import f1porra.f1.Clasification.UserClasificationGP
import f1porra.f1.GrandPrix

class ClasificationController {

    def springSecurityService

    def index() {

        User actualUser = User.get(springSecurityService.getPrincipal().id)
        def actualSeason = new Date().getYear()+1900

        def grandPrixesSeason = GrandPrix.findAllBySeason(actualSeason, [sort:"raceStart", order:"asc"])



        render (view: "viewClasification", model:[actualUser : actualUser, actualSeason : actualSeason, grandPrixesSeason : grandPrixesSeason])

    }
}
