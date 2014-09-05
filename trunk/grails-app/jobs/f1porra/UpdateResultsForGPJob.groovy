package f1porra

import f1porra.f1.GrandPrix



class UpdateResultsForGPJob {
    static triggers = {
    }

    def retrieveResultsInfoService
    def clasificationService

    def execute(context) {
        String gpId = context.mergedJobDataMap.get('gpId')
        gpId = gpId.toInteger()
        GrandPrix gpToProcess = GrandPrix.findById(gpId)

        log.info "Ejecutando job de actualización de resultados para el GP " + gpToProcess.name
        retrieveResultsInfoService.retrieveResults("http://www.formula1.com/results/season/")
        log.info "Resultados actualizados"

        log.info "Recalculando puntuaciones"
        clasificationService.processUsersForGP(gpToProcess)
        log.info "Puntuaciones calculadas"
    }
}
