package f1porra.userControllers

import f1porra.Billing
import f1porra.User
import f1porra.f1.Driver
import f1porra.f1.DriverHierarchy
import f1porra.f1.GrandPrix

class BillingsController {

    def springSecurityService

    def index() {

        def actualSeason = new Date().getYear()+1900

        def gpList = GrandPrix.findAllBySeason(actualSeason, [sort:"raceStart", order:"asc"])

        User actualUser = User.get(springSecurityService.getPrincipal().id)


        Date nowTime = new Date()

        def userBillings = Billing.findAllByUserBill(actualUser)

        GrandPrix nextGP = null

        int iterator = 0


        while (iterator < gpList.size())
        {
            if (gpList[iterator].raceStart > nowTime)
            {
                nextGP = gpList[iterator]
                break
            }
            iterator++
        }

        render(view: "viewGPs", model: [gpList : gpList, nowTime : nowTime, userBillings: userBillings, nextGP : nextGP])

    }



    def voteFormulary()
    {

        def driversList = Driver.findAllByState("active", [sort: "team.name", order: "asc"])
        def gpId = params.gpId

        GrandPrix actualGP = GrandPrix.findById(gpId)
        if (actualGP == null)
        {
            flash.error = message(code: "default.billings.notFoundGP", args: [])
            redirect(controller: "billings", action: "index")
        }
        //def userId = User.get(springSecurityService.principal.id)
        User actualUser = User.get(springSecurityService.getPrincipal().id)

        Billing userBilling

        if (actualUser != null)
            userBilling = Billing.findByUserBillAndGrandPrix(actualUser, actualGP)

        render (view : "voteFormulary", model : [driversList : driversList, actualGP : actualGP, userBilling : userBilling])
    }

    def voteResults()
    {
        def gpId = params.gpId

        GrandPrix actualGP = GrandPrix.findById(gpId)
        if (actualGP == null)
        {
            flash.error = message(code: "default.billings.notFoundGP", args: [])
            redirect(controller: "billings", action: "index")
        }
        User actualUser = User.get(springSecurityService.getPrincipal().id)

        Billing userBilling

        if (actualUser != null)
            userBilling = Billing.findByUserBillAndGrandPrix(actualUser, actualGP)

        render (view : "viewVoteResults", model : [actualGP : actualGP, userBilling : userBilling])
    }




    def vote()
    {
        def gpId = params.gpId
        gpId = gpId.toInteger()
        def actualUser = User.get(springSecurityService.getPrincipal().id)
        def gpToVote = GrandPrix.findById(gpId)

        if (gpToVote.finishPole < (new Date()))
        {
            flash.error = message(code: "default.billings.notAllowedActionDateExpired", args: [])
            redirect(controller: "billings", action: "index")
            return
        }


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


        }
        catch (Exception e)
        {
            flash.error = "Error voting. Please fill all values."
            redirect(controller: "billings", action: "voteFormulary", params : [gpId : gpToVote.id])
            return
        }



        newBilling.fastLapBill = Driver.findById(params.fastLap.toInteger())
        if (newBilling.raceBill.save(flush: true) == null)
        {
            flash.error = message(code: "default.billings.unexpectedError", args: [])
            redirect(controller: "billings", action: "index")
            return
        }
        if (newBilling.qualyBill.save(flush: true) == null)
        {
            flash.error = message(code: "default.billings.unexpectedError", args: [])
            redirect(controller: "billings", action: "index")
            return
        }

        if (newBilling.save(flush:true) == null)
        {
            flash.error = message(code: "default.billings.unexpectedError", args: [])
            redirect(controller: "billings", action: "index")
            return
        }

        actualUser.addToBillings(newBilling)
        if (actualUser.save(flush:true) == null)
        {
            flash.error = message(code: "default.billings.unexpectedError", args: [])
            redirect(controller: "billings", action: "index")
            return
        }

        flash.message = message(code: "default.billings.successVoting", args: [])
        redirect (controller: "billings", action: "voteFormulary", params: [gpId : params.gpId])
    }



    def voteResultsOU()
    {
        def gpId = params.gpId
        def userId = params.userId

        GrandPrix actualGP = GrandPrix.findById(gpId)
        if (actualGP == null)
        {
            flash.error = message(code: "default.billings.notFoundGP", args: [])
            redirect(controller: "billings", action: "index")
        }
        User actualUser = User.findById(userId)

        Billing userBilling

        if (actualUser != null)
            userBilling = Billing.findByUserBillAndGrandPrix(actualUser, actualGP)

        render (view : "viewVoteResults", model : [actualGP : actualGP, userBilling : userBilling])
    }




}
