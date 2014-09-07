import f1porra.ConfigParams
import f1porra.Role
import f1porra.User
import f1porra.UserRole


class BootStrap {

    def scheduleService
    def retrieveDriversInfoService

    def init = { servletContext ->

        //********************************************************************************
        //****************Roles y usuarios************************************************

        Role adminRole = Role.findByAuthority('ROLE_ADMIN')
        if (adminRole == null)
        {
            adminRole = new Role(authority: 'ROLE_ADMIN')
            if (adminRole.save(flush:true) == null)
                log.error("Error creando rol de administrador: " + adminRole.errors)
        }

        Role userRole = Role.findByAuthority('ROLE_USER')
        if (userRole == null)
        {
            userRole = new Role(authority: 'ROLE_USER')
            if (userRole.save(flush:true) == null)
                log.error("Error creando rol de usuario: " + userRole.errors)
        }

        Role facebookRole = Role.findByAuthority('ROLE_FACEBOOK')
        if (facebookRole == null)
        {
            facebookRole = new Role(authority: 'ROLE_FACEBOOK')
            if (facebookRole.save(flush:true) == null)
                log.error("Error creando rol de usuarioFacebook: " + facebookRole.errors)
        }

        User adminUser = User.findByUsername("admin")
        if (adminUser == null)
        {
            adminUser = new User(username: 'admin', password: 'adminvga4600', name: 'administrator', enabled : true)
            if (adminUser.save(flush:true) == null)
                System.out.println(adminUser.errors)

            UserRole.create adminUser, adminRole, true
        }

        assert Role.count() == 3
        scheduleService.scheduleGPsAndRecordatorysJobs()

        //Vamos a analizar si hay parámetros de configuración guardados
        ConfigParams configParams
        if (ConfigParams.count == 0)
        {
            log.info "No encontrados parámetros de configuración. Guardando valores por defecto"
            configParams = new ConfigParams()
            configParams.retrieveDriversInfoURL = "http://www.formula1.com/teams_and_drivers/drivers/"
            configParams.retrieveInfoGPs = "http://www.formula1.com/races/calendar.html"
            configParams.retrieveResultsURL = "http://www.formula1.com/results/season/"

            if (configParams.save(flush:true) == null)
            {
                log.error "Error salvando parámetros de configuración: " + configParams.errors
                return
            }
            else
            {
                log.info "Parámetros de configuración guardados con éxito."
            }
        }
        else
        {
            configParams = ConfigParams.first()
        }
        retrieveDriversInfoService.retrieveInfoDrivers(configParams.retrieveDriversInfoURL)
    }
    def destroy = {
    }
}
