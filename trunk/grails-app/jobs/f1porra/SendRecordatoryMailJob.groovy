package f1porra

import f1porra.f1.GrandPrix



class SendRecordatoryMailJob {
    static triggers = {
    }

    def adminF1Service

    def execute(context) {
        String gpId = context.mergedJobDataMap.get('gpId')
        gpId = gpId.toInteger()
        GrandPrix gpToProcess = GrandPrix.findById(gpId)

        log.info "Ejecutando job de recordatorio de votación"
        adminF1Service.recordatoryToVote(gpToProcess)
        log.info "Job finalizado"
    }
}
