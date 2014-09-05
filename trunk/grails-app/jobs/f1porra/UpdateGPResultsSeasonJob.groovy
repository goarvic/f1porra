package f1porra



class UpdateGPResultsSeasonJob {
    static triggers = {
    }

    def retrieveResultsInfoService
    def clasificationService

    def execute() {
        log.info "Ejecutando job de actualización de resultados"
        retrieveResultsInfoService.retrieveResults("http://www.formula1.com/results/season/")
        log.info "Resultados actualizados"

        log.info "Recalculando puntuaciones"
        def actualSeason = new Date().getYear() + 1900
        clasificationService.processClasificationPerUserInSeason(actualSeason)
        log.info "Puntuaciones calculadas"
    }
}
