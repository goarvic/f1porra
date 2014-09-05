package f1porra

import f1porra.f1.GrandPrix
import grails.transaction.Transactional

@Transactional
class ScheduleService {



    def scheduleGPsAndRecordatorysJobs() {

        Date now = new Date()
        def gpsToScheduleJobs = GrandPrix.findAllByFreePracticeStartGreaterThan(now)

        gpsToScheduleJobs.each {gpDetails->
            Date dateToProgramJob = new Date(gpDetails.qualyStart.getTime() + 7200000)
            UpdateResultsForGPJob.schedule(dateToProgramJob, [gpId : gpDetails.id])

            dateToProgramJob = new Date(gpDetails.raceStart.getTime() + 7200000)
            UpdateResultsForGPJob.schedule(dateToProgramJob,  [gpId : gpDetails.id])

            dateToProgramJob = new Date(gpDetails.raceStart.getTime() + 14400000)
            UpdateResultsForGPJob.schedule(dateToProgramJob, [gpId : gpDetails.id])

            dateToProgramJob = new Date(gpDetails.raceStart.getTime() + 86400000)
            UpdateResultsForGPJob.schedule(dateToProgramJob, [gpId : gpDetails.id])

            dateToProgramJob = new Date(gpDetails.freePracticeStart.getTime() - 86400000)
            SendRecordatoryMailJob.schedule(dateToProgramJob, [gpId : gpDetails.id])
        }
    }
}
