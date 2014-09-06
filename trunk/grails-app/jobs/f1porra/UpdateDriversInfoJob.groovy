package f1porra


class UpdateDriversInfoJob {
    static triggers = {
        cron name: 'updateDriversInfoTrigger', cronExpression: "0 0 4 * * ?"
    }

    def retrieveDriversInfoService

    def execute() {

        ConfigParams configParams

        if (ConfigParams.count == 0)
        {
            log.warn "No se puede ejecutar el job de actualización de pilotos porque no se han encontrado los parámetros de configuración en BBDD"
            return
        }
        else
            configParams = ConfigParams.first()

        if (configParams.retrieveDriversInfoURL != null)
            retrieveDriversInfoService.retrieveInfoDrivers(configParams.retrieveDriversInfoURL)
    }
}
