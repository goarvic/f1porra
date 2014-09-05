package f1porra.adminControllers

import f1porra.Billing
import f1porra.User
import f1porra.f1.Driver
import f1porra.f1.DriverHierarchy
import f1porra.f1.GrandPrix

class ManageVotesController {

    def index()
    {
        def users = User.list(sort:"username", order:"asc")
        def actualSeason = new Date().getYear()+1900
        def gpList = GrandPrix.findAllBySeason(actualSeason, [sort:"raceStart", order:"asc"])



        render (view: "selectUserAndGP", model:[users : users, gpList : gpList])
    }



    def voteFormulary()
    {

        def driversList = Driver.list(sort: "team.name", order: "asc")
        def gpId = params.gpId
        def userId = params.userId

        GrandPrix actualGP = GrandPrix.findById(gpId)
        if (actualGP == null)
        {
            flash.error = "Error: Grand Prix Not Found"
            redirect(controller: "manageVotes", action: "index")
        }

        User actualUser = User.findById(userId)

        Billing userBilling

        if (actualUser != null)
            userBilling = Billing.findByUserBillAndGrandPrix(actualUser, actualGP)

        render (view : "voteFormulary", model : [actualUser : actualUser, driversList : driversList, actualGP : actualGP, userBilling : userBilling])
    }



    def vote()
    {
        def gpId = params.gpId
        gpId = gpId.toInteger()
        def userId = params.userId.toInteger()
        def actualUser = User.findById(userId)
        def gpToVote = GrandPrix.findById(gpId)

        Billing newBilling = Billing.findByGrandPrixAndUserBill(gpToVote,actualUser)
        if (newBilling == null)
        {
            DriverHierarchy qualyVote = new DriverHierarchy()
            DriverHierarchy raceVote = new DriverHierarchy()

            newBilling = new Billing(grandPrix: gpToVote, userBill: actualUser,qualyBill: qualyVote, raceBill: raceVote)
        }

        try
        {
            newBilling.qualyBill.pos1 = Driver.findById(params.pos1_qualy.toInteger())
            newBilling.qualyBill.pos2 = Driver.findById(params.pos2_qualy.toInteger())
            newBilling.qualyBill.pos3 = Driver.findById(params.pos3_qualy.toInteger())
            newBilling.qualyBill.pos4 = Driver.findById(params.pos4_qualy.toInteger())
            newBilling.qualyBill.pos5 = Driver.findById(params.pos5_qualy.toInteger())
            newBilling.qualyBill.pos6 = Driver.findById(params.pos6_qualy.toInteger())
            newBilling.qualyBill.pos7 = Driver.findById(params.pos7_qualy.toInteger())
            newBilling.qualyBill.pos8 = Driver.findById(params.pos8_qualy.toInteger())

            newBilling.raceBill.pos1 = Driver.findById(params.pos1_race.toInteger())
            newBilling.raceBill.pos2 = Driver.findById(params.pos2_race.toInteger())
            newBilling.raceBill.pos3 = Driver.findById(params.pos3_race.toInteger())
            newBilling.raceBill.pos4 = Driver.findById(params.pos4_race.toInteger())
            newBilling.raceBill.pos5 = Driver.findById(params.pos5_race.toInteger())
            newBilling.raceBill.pos6 = Driver.findById(params.pos6_race.toInteger())
            newBilling.raceBill.pos7 = Driver.findById(params.pos7_race.toInteger())
            newBilling.raceBill.pos8 = Driver.findById(params.pos8_race.toInteger())

            newBilling.fastLapBill = Driver.findById(params.fastLap.toInteger())
        }
        catch (Exception e)
        {
            flash.error = "Error voting. Please fill all values."
            redirect( controller: "manageVotes", action: "voteFormulary", params : [gpId : gpToVote.id, userId: actualUser.id])
            return
        }

        if (newBilling.raceBill.save(flush: true) == null)
        {
            flash.error = "Unexpected error saving vote"
            redirect(controller: "manageVotes", action: "index")
            return
        }
        if (newBilling.qualyBill.save(flush: true) == null)
        {
            flash.error = "Unexpected error saving vote"
            redirect(controller: "manageVotes", action: "index")
            return
        }

        if (newBilling.save(flush:true) == null)
        {
            flash.error = "Unexpected error saving vote"
            redirect(controller: "manageVotes", action: "index")
            return
        }

        actualUser.addToBillings(newBilling)
        if (actualUser.save(flush:true) == null)
        {
            flash.error = "Unexpected error saving vote"
            redirect(controller: "manageVotes", action: "index")
            return
        }

        flash.message = "Success. Vote Correct!"
        redirect (controller: "manageVotes", action: "voteFormulary", params: [gpId : params.gpId, userId : userId])
    }
}
