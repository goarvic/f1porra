package f1porra

import f1porra.f1.Driver
import f1porra.f1.Team
import grails.transaction.Transactional


@Transactional
class RetrieveDriversInfoService {

    void retrieveInfoDrivers(String url) {



        def parser = new org.cyberneko.html.parsers.SAXParser()
        parser.setFeature('http://xml.org/sax/features/namespaces', false)
        def page = new XmlParser(parser).parse(url)

        ArrayList<groovy.util.Node> driversSections = page. depthFirst().UL.grep{it.'@class' == 'driverMugShot'}

        if (driversSections.size() == 0)
        {
            log.error "Error en la actualización de pilotos. Sección no encontrada en la página de F1. Es conveniete revisar el parseo."
            return
        }
        else if (driversSections.size() > 1)
        {
            log.error "Hay mas de una seccion de pilotos en la página de F1. Revisar el parseo sería conveniente"
        }

        def drivers = Driver.findAllByState("active")


        for (Driver driver : drivers)
        {
            driver.state = "inactive"
            if (driver.save(flush:true) == null)
            {
                log.error "Error guardando estado de piloto en BBDD: " + driver.errors
                return
            }
        }

        groovy.util.Node driverSection = driversSections.get(0)
        def driversInfo = driverSection.depthFirst().DIV


        for (groovy.util.Node node : driversInfo)
        {
            def driverName = node.depthFirst().IMG."@alt"[0]
            def teamName = node.depthFirst().A[1].SPAN.text()

            Team team = Team.findByName(teamName)

            if (team == null)
            {
                log.info "El equipo no se ha encontrado. Puede que sea nuevo o haya cambiado el nombre. Se procede a guardar uno nuevo."
                team = new Team(name: teamName, logoName: teamName+".jpg")
                if (team.save(flush:true) == null)
                {
                    log.error "Error salvando nuevo equipo: " + team.errors
                    return
                }
            }

            Driver driver = Driver.findByName(driverName)
            if (driver == null)
            {
                log.info "El piloto no se ha encontrado. Puede que sea nuevo o haya cambiado el nombre en la web. Se procede a guardar uno nuevo."
                driver = new Driver(name: driverName, state: "active", imageName: null, team: team)
            }
            else
            {
                driver.state = "active"
                driver.team = team
            }

            if (driver.save(flush:true) == null)
            {
                log.error "Error salvando piloto"
                return
            }
        }

        log.info "Actualización de pilotos correcta"
    }
}
