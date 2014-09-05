package f1porra.adminControllers

import f1porra.Billing
import f1porra.SendRecordatoryMailJob
import f1porra.UpdateGPResultsSeasonJob
import f1porra.UpdateResultsForGPJob
import f1porra.User
import f1porra.f1.Driver
import f1porra.f1.DriverHierarchy
import f1porra.f1.GrandPrix

class TestController {

    def clasificationService
    def adminF1Service

    def insertVotes() {

        User vickop = User.findByEmail("goarvic@gmail.com")
        User vga = User.findByEmail("vga@softtelecom.eu")


        GrandPrix Malasia = GrandPrix.findById(2)

        Billing billingToSave = Billing.findByUserBillAndGrandPrix(vickop, Malasia)

        if (billingToSave != null)
        {
            request.error = "Ya existe apuesta. Imposible guardar"
            render (view: "../index")
            return
        }

        billingToSave = Billing.findByUserBillAndGrandPrix(vga, Malasia)
        if (billingToSave != null)
        {
            request.error = "Ya existe apuesta. Imposible guardar"
            render (view: "../index")
            return
        }

        Driver fAlonso = Driver.findByName("Fernando Alonso")
        Driver nRosberg = Driver.findByName("Nico Rosberg")
        Driver kMagnussen = Driver.findByName("Kevin Magnussen")
        Driver jButton = Driver.findByName("Jenson Button")
        Driver vBottas = Driver.findByName("Valtteri  Bottas")
        Driver nHulkenberg = Driver.findByName("Nico Hulkenberg")
        Driver kRaikkonen = Driver.findByName("Kimi Räikkönen")
        Driver jeVergne = Driver.findByName("Jean-Éric Vergne")
        Driver dKvyat = Driver.findByName("Daniil Kvyat")
        Driver sPerez = Driver.findByName("Sergio Perez")
        Driver pMaldonado = Driver.findByName("Pastor Maldonado")
        Driver eGutierrez = Driver.findByName("Esteban Gutierrez")
        Driver mEricsson = Driver.findByName("Marcus Ericsson")
        Driver kKobayashi = Driver.findByName("Kamui Kobayashi")
        Driver lHamilton = Driver.findByName("Lewis Hamilton")
        Driver jBianchi = Driver.findByName("Jules Bianchi")
        Driver mChilton = Driver.findByName("Max Chilton")
        Driver fMassa = Driver.findByName("Felipe Massa")
        Driver aSutil = Driver.findByName("Adrian Sutil")
        Driver dRicciardo = Driver.findByName("Daniel Ricciardo")
        Driver sVettel = Driver.findByName("Sebastian Vettel")
        Driver rGrosjean = Driver.findByName("Romain Grosjean")

        billingToSave = new Billing(userBill: vickop, grandPrix: Malasia)
        DriverHierarchy qualy = new DriverHierarchy(pos1: lHamilton, pos2: nRosberg, pos3: kMagnussen, pos4: fAlonso,
                                                    pos5: dRicciardo, pos6: kRaikkonen, pos7: sVettel, pos8: fMassa)
        DriverHierarchy race = new DriverHierarchy(pos1: nRosberg, pos2: lHamilton, pos3: fAlonso, pos4: kRaikkonen,
                                                    pos5: fMassa, pos6: kMagnussen, pos7: dRicciardo, pos8: sVettel)
        if (qualy.save(flush:true) == null)
        {
            log.error "Error salvando: " + qualy.errors
            render (view: "../index")
            return
        }

        if (race.save(flush:true) == null)
        {
            log.error "Error salvando: " + race.errors
            render (view: "../index")
            return
        }

        billingToSave.raceBill = race
        billingToSave.qualyBill = qualy
        billingToSave.fastLapBill = lHamilton

        if (billingToSave.save(flush:true) == null)
        {
            log.error "Error salvando: " + billingToSave.errors
            render (view: "../index")
            return
        }
        vickop.addToBillings(billingToSave)
        vickop.save(flush:true)

        //**********************************************************************************

        billingToSave = new Billing(userBill: vga, grandPrix: Malasia)
        qualy = new DriverHierarchy(pos1: lHamilton, pos2: nRosberg, pos3: kMagnussen, pos4: sVettel,
                pos5: dRicciardo, pos6: fAlonso, pos7: vBottas, pos8: fMassa)
        race = new DriverHierarchy(pos1: lHamilton, pos2: nRosberg, pos3: sVettel, pos4: vBottas,
                pos5: fAlonso, pos6: fMassa, pos7: kRaikkonen, pos8: jButton)
        if (qualy.save(flush:true) == null)
        {
            log.error "Error salvando: " + qualy.errors
            render (view: "../index")
            return
        }

        if (race.save(flush:true) == null)
        {
            log.error "Error salvando: " + race.errors
            render (view: "../index")
            return
        }

        billingToSave.raceBill = race
        billingToSave.qualyBill = qualy
        billingToSave.fastLapBill = lHamilton

        if (billingToSave.save(flush:true) == null)
        {
            log.error "Error salvando: " + billingToSave.errors
            render (view: "../index")
            return
        }

        vga.addToBillings(billingToSave)
        vga.save(flush:true)





        render (view: "../index")
    }

    def processResults()
    {

        clasificationService.processClasificationPerUserInSeason(2014)

        request.message = "Parece que ok"
        render (view: "../index")
    }


    def programJob()
    {
        Date nowPlus2Minutes = new Date()

        HashMap params = new HashMap();

        nowPlus2Minutes = new Date(nowPlus2Minutes.getTime() + 30000)
        //UpdateGPResultsSeasonJob.schedule(nowPlus2Minutes)

        def gpDetails = GrandPrix.findById(13)

        params.put("gpId", gpDetails.id.toString()); //this will throw compiler error because 21.0 is not integer
        log.info gpDetails.id.toString()

        SendRecordatoryMailJob.schedule(nowPlus2Minutes, params)

        //UpdateResultsForGPJob.schedule(nowPlus2Minutes, [gpId : 1])
        request.message = "Parece que se ha programado"

        render(view: "../index", model:[])
    }


    def sendMail(){
        GrandPrix gp = GrandPrix.findById(13)
        adminF1Service.recordatoryToVote(gp)

        request.message = "Corretto"

        render(view: "../index", model:[])

    }
}
