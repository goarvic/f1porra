import f1porra.Role
import f1porra.User
import f1porra.UserRole
import f1porra.f1.DriverHierarchy
import f1porra.f1.GrandPrix
import  f1porra.f1.Team
import  f1porra.f1.Driver

class BootStrap {

    def scheduleService

    def init = { servletContext ->

        //*********************************************************************
        //****************Roles y usuarios************************************************
        Role adminRole = Role.findByAuthority('ROLE_ADMIN')
        if (adminRole == null)
        {
            adminRole = new Role(authority: 'ROLE_ADMIN')
            if (adminRole.save(flush:true) == null)
                System.out.println(adminRole.errors)
        }

        Role userRole = Role.findByAuthority('ROLE_USER')
        if (userRole == null)
        {
            userRole = new Role(authority: 'ROLE_USER')
            (userRole.save(flush:true) == null)
                System.out.println(userRole.errors)
        }

        User adminUser = User.findByUsername("admin")
        if (adminUser == null)
        {
            adminUser = new User(username: 'admin', password: 'adminvga4600', name: 'administrator', enabled : true)
            if (adminUser.save(flush:true) == null)
                System.out.println(adminUser.errors)

            UserRole.create adminUser, adminRole, true
        }

        assert Role.count() == 2

        //*********************************************************************
        //****************Equipos y pilotos**********************************************

        /*
        Driver fAlonso
        Driver nRosberg
        Driver kMagnussen
        Driver jButton
        Driver vBottas
        Driver nHulkenberg
        Driver kRaikkonen
        Driver jeVergne
        Driver dKvyat
        Driver sPerez
        Driver pMaldonado
        Driver eGutierrez
        Driver mEricsson
        Driver kKobayashi
        Driver lHamilton
        Driver jBianchi
        Driver mChilton
        Driver fMassa
        Driver aSutil
        Driver dRicciardo
        Driver sVettel
        Driver rGrosjean

        if (Team.count() == 0)
        {
            Team teamFerrari =      new Team(name: "Ferrari",       logoName: "Ferrari.jpg").save(flush: true)
            Team teamMclaren =      new Team(name: "McLaren",       logoName: "McLaren.jpg").save(flush: true)
            Team teamMercedes =     new Team(name: "Mercedes AMG",  logoName: "Mercedes.jpg").save(flush: true)
            Team teamWilliams =     new Team(name: "Williams",      logoName: "Williams.jpg").save(flush: true)
            Team teamRedBull =      new Team(name: "Red Bull",      logoName: "RedBull.jpg").save(flush: true)
            Team teamToroRosso =    new Team(name: "Toro Rosso",    logoName: "ToroRosso.jpg").save(flush: true)
            Team teamForceIndia =   new Team(name: "Force India",   logoName: "ForceIndia.jpg").save(flush: true)
            Team teamLotus =        new Team(name: "Lotus",         logoName: "Lotus.jpg").save(flush: true)
            Team teamCaterham =     new Team(name: "Caterham",      logoName: "Caterham.jpg").save(flush: true)
            Team teamSauber =       new Team(name: "Sauber",        logoName: "Sauber.jpg").save(flush: true)
            Team teamMarussia =     new Team(name: "Marussia",      logoName: "Marussia.jpg").save(flush: true)


            fAlonso =       new Driver( name: "Fernando Alonso",     team : teamFerrari,     imageName: "Fernando.jpg",      state : "active").save(flush: true)
            nRosberg =      new Driver( name: "Nico Rosberg",        team : teamMercedes,    imageName: "Rosberg.jpg",       state : "active").save(flush: true)
            kMagnussen =    new Driver( name: "Kevin Magnussen",     team : teamMclaren,     imageName: "Magnussen.jpg",     state : "active").save(flush: true)
            jButton =       new Driver( name: "Jenson Button",       team : teamMclaren,     imageName: "Button.jpg",        state : "active").save(flush: true)
            vBottas =       new Driver( name: "Valtteri  Bottas",     team : teamWilliams,    imageName: "Bottas.jpg",        state : "active").save(flush: true)
            nHulkenberg =   new Driver( name: "Nico Hulkenberg",     team : teamForceIndia,  imageName: "Hulkenberg.jpg",    state : "active").save(flush: true)
            kRaikkonen =    new Driver( name: "Kimi Räikkönen",      team : teamFerrari,     imageName: "Räikkonen.jpg",     state : "active").save(flush: true)
            jeVergne =      new Driver( name: "Jean-Éric Vergne",    team : teamToroRosso,   imageName: "Vergne.jpg",        state : "active").save(flush: true)
            dKvyat =        new Driver( name: "Daniil Kvyat",        team : teamToroRosso,   imageName: "Kvyat.jpg",         state : "active").save(flush: true)
            sPerez =        new Driver( name: "Sergio Perez",        team : teamForceIndia,  imageName: "Perez.jpg",         state : "active").save(flush: true)
            pMaldonado =    new Driver( name: "Pastor Maldonado",    team : teamLotus,       imageName: "Maldonado.jpg",     state : "active").save(flush: true)
            eGutierrez =    new Driver( name: "Esteban Gutierrez",   team : teamSauber,      imageName: "Gutierrez.jpg",     state : "active").save(flush: true)
            mEricsson =     new Driver( name: "Marcus Ericsson",     team : teamCaterham,    imageName: "Ericsson.jpg",      state : "active").save(flush: true)
            kKobayashi =    new Driver( name: "Kamui Kobayashi",     team : teamCaterham,    imageName: "Kobayashi.jpg",     state : "active").save(flush: true)
            lHamilton =     new Driver( name: "Lewis Hamilton",      team : teamMercedes,    imageName: "Hamilton.jpg",      state : "active").save(flush: true)
            jBianchi =      new Driver( name: "Jules Bianchi",       team : teamMarussia,    imageName: "Bianchi.jpg",       state : "active").save(flush: true)
            mChilton =      new Driver( name: "Max Chilton",         team : teamMarussia,    imageName: "Chilton.jpg",       state : "active").save(flush: true)
            fMassa =        new Driver( name: "Felipe Massa",        team : teamWilliams,    imageName: "Massa.jpg",         state : "active").save(flush: true)
            aSutil =        new Driver( name: "Adrian Sutil",        team : teamSauber,      imageName: "Sutil.jpg",         state : "active").save(flush: true)
            dRicciardo =    new Driver( name: "Daniel Ricciardo",    team : teamRedBull,     imageName: "Ricciardo.jpg",     state : "active").save(flush: true)
            sVettel =       new Driver( name: "Sebastian Vettel",    team : teamRedBull,     imageName: "Vettel.jpg",        state : "active").save(flush: true)
            rGrosjean =     new Driver( name: "Romain Grosjean",     team : teamLotus,       imageName: "Grosjean.jpg",      state : "active").save(flush: true)
        }
        else
        {
            fAlonso = Driver.findByName("Fernando Alonso")
            nRosberg = Driver.findByName("Nico Rosberg")
            kMagnussen = Driver.findByName("Kevin Magnussen")
            jButton = Driver.findByName("Jenson Button")
            vBottas = Driver.findByName("Valtteri Bottas")
            nHulkenberg = Driver.findByName("Nico Hülkenberg")
            kRaikkonen = Driver.findByName("Kimi Räikkönen")
            jeVergne = Driver.findByName("Jean-Éric Vergne")
            dKvyat = Driver.findByName("Danill Kvyat")
            sPerez = Driver.findByName("Sergio Pérez")
            pMaldonado = Driver.findByName("Pastor Maldonado")
            eGutierrez = Driver.findByName("Esteban Gutiérrez")
            mEricsson = Driver.findByName("Marcus Ericsson")
            kKobayashi = Driver.findByName("Kamui Kobayashi")
            lHamilton = Driver.findByName("Lewis Hamilton")
            jBianchi = Driver.findByName("Jules Bianchi")
            mChilton = Driver.findByName("Max Chilton")
            fMassa = Driver.findByName("Felipe Massa")
            aSutil = Driver.findByName("Adrian Sutil")
            dRicciardo = Driver.findByName("Daniel Ricciardo")
            sVettel = Driver.findByName("Sebastian Vettel")
            rGrosjean = Driver.findByName("Romain Grosjean")
        }*/

        //*******************Vamos a añadir grandes premios

        /*if (GrandPrix.count() == 0)
        {

            DriverHierarchy raceResult = new DriverHierarchy(pos1: nRosberg, pos2: kMagnussen, pos3: jButton, pos4: fAlonso, pos5: vBottas,
                    pos6: nHulkenberg, pos7: kRaikkonen, pos8: jeVergne, pos9: dKvyat, pos10: sPerez).save(flush:true)

            DriverHierarchy qualyResult = new DriverHierarchy(pos1: lHamilton, pos2: dRicciardo, pos3: nRosberg, pos4: kMagnussen, pos5: fAlonso,
                    pos6: jeVergne, pos7: nHulkenberg, pos8: dKvyat, pos9: fMassa, pos10: vBottas).save(flush:true)

            GrandPrix newGP
            newGP = new GrandPrix(name: "Gran Premio de Australia - Melbourne", qualyStart: new Date(114, 2, 15, 6, 00),
                                                                                freePracticeStart: new Date(114,2,14,1,30), raceStart: new Date(114,2,16,7,00),
                                                                                qualyResult: qualyResult, raceResult : raceResult)

            if (newGP.save(flush: true) == null)
            {
                System.out.println(newGP.errors)
            }

            newGP = new GrandPrix(name: "Gran Premio de Malasia", qualyStart: new Date(114, 2, 29, 9, 00),
                    circuitImageName: "Malasia.svg", freePracticeStart: new Date(114,2,28,3,00), raceStart: new Date(114,2,29,10,00)).save(flush: true)

            newGP = new GrandPrix(name: "Gran Premio de Bahrein - Manama", qualyStart: new Date(114, 3, 5, 16, 00),
                    circuitImageName: "Bahrein.png", freePracticeStart: new Date(114,3,4,12,00), raceStart: new Date(114,3,6,16,00)).save(flush: true)

        } */



        //A programar Jobs

        scheduleService.scheduleGPsAndRecordatorysJobs()



    }
    def destroy = {
    }
}
