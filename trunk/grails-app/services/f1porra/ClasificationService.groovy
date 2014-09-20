package f1porra

import f1porra.f1.Clasification.UserClasificationGP
import f1porra.f1.GrandPrix
import grails.transaction.Transactional

@Transactional
class ClasificationService {

    int isTheBetterInClassInGroup(GrandPrix gpToProcess, GroupV group, User userToProcess, Billing userBilling)
    {
        def hintsOfUser = 0

        if (userBilling.qualyBill.pos1 == gpToProcess.qualyResult.pos1)
        {
            hintsOfUser++
        }

        if (userBilling.qualyBill.pos2 == gpToProcess.qualyResult.pos2)
        {
            hintsOfUser++
        }

        if (userBilling.qualyBill.pos3 == gpToProcess.qualyResult.pos3)
        {
            hintsOfUser++
        }

        if (userBilling.qualyBill.pos4 == gpToProcess.qualyResult.pos4)
        {
            hintsOfUser++
        }

        if (userBilling.qualyBill.pos5 == gpToProcess.qualyResult.pos5)
        {
            hintsOfUser++
        }

        if (userBilling.qualyBill.pos6 == gpToProcess.qualyResult.pos6)
        {
            hintsOfUser++
        }

        if (userBilling.qualyBill.pos7 == gpToProcess.qualyResult.pos7)
        {
            hintsOfUser++
        }

        if (userBilling.qualyBill.pos8 == gpToProcess.qualyResult.pos8)
        {
            hintsOfUser++
        }

        def usersInGroup = group.users
        def maximumHitsInGroup = 0


        for (def i= 0; i<usersInGroup.size(); i++)
        {
            if (usersInGroup[i] != userToProcess)
            {
                Billing userInGroupBilling = Billing.findByGrandPrixAndUserBill(gpToProcess, usersInGroup[i])
                if (userInGroupBilling != null)
                {
                    def hintsOfUserGroup = 0
                    if (userInGroupBilling.qualyBill.pos1 == gpToProcess.qualyResult.pos1)
                    {
                        hintsOfUserGroup++
                    }

                    if (userInGroupBilling.qualyBill.pos2 == gpToProcess.qualyResult.pos2)
                    {
                        hintsOfUserGroup++
                    }

                    if (userInGroupBilling.qualyBill.pos3 == gpToProcess.qualyResult.pos3)
                    {
                        hintsOfUserGroup++
                    }

                    if (userInGroupBilling.qualyBill.pos4 == gpToProcess.qualyResult.pos4)
                    {
                        hintsOfUserGroup++
                    }

                    if (userInGroupBilling.qualyBill.pos5 == gpToProcess.qualyResult.pos5)
                    {
                        hintsOfUserGroup++
                    }

                    if (userInGroupBilling.qualyBill.pos6 == gpToProcess.qualyResult.pos6)
                    {
                        hintsOfUserGroup++
                    }

                    if (userInGroupBilling.qualyBill.pos7 == gpToProcess.qualyResult.pos7)
                    {
                        hintsOfUserGroup++
                    }

                    if (userInGroupBilling.qualyBill.pos8 == gpToProcess.qualyResult.pos8)
                    {
                        hintsOfUserGroup++
                    }

                    if (maximumHitsInGroup < hintsOfUserGroup)
                        maximumHitsInGroup = hintsOfUserGroup
                }
            }
        }

        if (maximumHitsInGroup <= hintsOfUser)
            return 1
        else
            return 0

    }



    //***********************************************************************************************
    //***********************************************************************************************
    //***********************************************************************************************
    //***********************************************************************************************


    int processUserGPInGroup(GrandPrix gpToProcess, User userToProcess, GroupV group)
    {
        if ((group.dateCreated != null)&&(group.dateCreated <= gpToProcess.raceStart))
        {
            UserClasificationGP userClasification = UserClasificationGP.findByUserAndGrandPrixAndGroup(userToProcess, gpToProcess, group)
            def isNew = 0
            if (userClasification == null)
            {
                userClasification = new UserClasificationGP(user: userToProcess, grandPrix: gpToProcess, group: group)
                isNew = 1
            }

            Billing userBilling = Billing.findByGrandPrixAndUserBill(gpToProcess, userToProcess)
            if (userBilling == null)
            {
                userClasification.points = 0
                if (userClasification.save(flush:true) == null)
                {
                    log.error "Error salvando puntuación: " + userClasification.errors
                    return -1
                }
                else
                {
                    if (isNew == 1)
                    {
                        group.addToUserClasifications(userClasification)
                        if (group.save(flush: true) == null)
                        {
                            log.error "Error salvando nueva puntuación: " + group.errors
                            return -1
                        }
                        userToProcess.addToUserClasifications(userClasification)
                        if (userToProcess.save(flush: true) == null)
                        {
                            log.error "Error salvando nueva puntuación: " + userToProcess.errors
                            return -1
                        }
                    }
                }
                return 0
            }

            def pointForClasification = isTheBetterInClassInGroup(gpToProcess, group, userToProcess,  userBilling)

            def pointsOfRace = 0

            def pointOfFastLap = 0

            if (userBilling.raceBill.pos1 == gpToProcess.raceResult.pos1)
            {
                pointsOfRace++
            }

            if (userBilling.raceBill.pos2 == gpToProcess.raceResult.pos2)
            {
                pointsOfRace++
            }

            if (userBilling.raceBill.pos3 == gpToProcess.raceResult.pos3)
            {
                pointsOfRace++
            }

            if (userBilling.raceBill.pos4 == gpToProcess.raceResult.pos4)
            {
                pointsOfRace++
            }

            if (userBilling.raceBill.pos5 == gpToProcess.raceResult.pos5)
            {
                pointsOfRace++
            }

            if (userBilling.raceBill.pos6 == gpToProcess.raceResult.pos6)
            {
                pointsOfRace++
            }

            if (userBilling.raceBill.pos7 == gpToProcess.raceResult.pos7)
            {
                pointsOfRace++
            }

            if (userBilling.raceBill.pos8 == gpToProcess.raceResult.pos8)
            {
                pointsOfRace++
            }

            if (userBilling.fastLapBill == gpToProcess.fastLap)
            {
                pointOfFastLap++
            }

            userClasification.points = pointOfFastLap + pointsOfRace + pointForClasification

            if (userClasification.save(flush:true) == null)
            {
                log.error "Error salvando puntuación: " + userClasification.errors
                return -1
            }

            if (isNew == 1)
            {
                group.addToUserClasifications(userClasification)
                if (group.save(flush: true) == null)
                {
                    log.error "Error salvando nueva puntuación: " + group.errors
                    return -1
                }
                userToProcess.addToUserClasifications(userClasification)
                if (userToProcess.save(flush: true) == null)
                {
                    log.error "Error salvando nueva puntuación: " + userToProcess.errors
                    return -1
                }
            }
            return userClasification.points
        }
        else
            return 0


    }


    //***********************************************************************************************
    //***********************************************************************************************
    //***********************************************************************************************
    //***********************************************************************************************



    def processUserGPInGroups(GrandPrix gpToProcess, User userToProcess)
    {

        def groupsOfUser = userToProcess.groupsInside

        groupsOfUser.each{groupOfUser->
            processUserGPInGroup(gpToProcess, userToProcess, groupOfUser)
        }

    }



    //***********************************************************************************************
    //***********************************************************************************************
    //***********************************************************************************************
    //***********************************************************************************************


    def processUsersForGP(GrandPrix gpToProcess)
    {


        def usersToProcess = User.list()

        usersToProcess.each{userToProcess->
                if (gpToProcess.raceResult != null)
                    processUserGPInGroups(gpToProcess, userToProcess)
        }

    }


    //***********************************************************************************************
    //***********************************************************************************************
    //***********************************************************************************************
    //***********************************************************************************************



    def processGPsUsersForGroup(int season, GroupV group)
    {

        def usersInsideGroup = group.users

        Date nowMinus2Hours = new Date()
        nowMinus2Hours = new Date(nowMinus2Hours.getTime() - 10800000)
        def gpsToProcess = GrandPrix.findAllBySeasonAndRaceStartLessThan(season,nowMinus2Hours)

        usersInsideGroup.each{userOfGroup->
            gpsToProcess.each{gpToProcess->
                if ((gpsToProcess.raceResult != null) && (gpsToProcess.fastLap != null) && (gpsToProcess.qualyResult != null))
                    processUserGPInGroup(gpToProcess, userOfGroup, group)
            }
        }

    }



    //***********************************************************************************************
    //***********************************************************************************************
    //***********************************************************************************************
    //***********************************************************************************************


    def processClasificationPerUserInSeason(int season) {

        Date nowMinus2Hours = new Date()
        nowMinus2Hours = new Date(nowMinus2Hours.getTime() - 10800000)
        def gpsToProcess = GrandPrix.findAllBySeasonAndRaceStartLessThan(season,nowMinus2Hours)

        def usersToProcess = User.list()

        usersToProcess.each{userToProcess->
            gpsToProcess.each{gpToProcess->
                if (gpToProcess.raceResult != null)
                    processUserGPInGroups(gpToProcess, userToProcess)
            }

        }

    }
}
