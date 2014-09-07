package f1porra

import f1porra.f1.GrandPrix
import f1porra.f1.Team
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

@Transactional
class AdminF1Service {

    LinkGenerator grailsLinkGenerator
    def mailService


    def saveNewTeam(String teamName, String imageName)
    {
        Team newTeam = new Team(name : teamName, logoName: imageName)

        if (newTeam.save(flush: true) == null)
        {
            log.error "Error salvando equipo: " + newTeam.errors
            return -1
        }

        return 0
    }



    def inviteUserToSystem(String userMail, String groupName)
    {

        if (userMail.indexOf("@") <= 0)
            return -1

        GroupV group = GroupV.findByName(groupName)

        if (group == null)
            return -1

        PreUser preUser = PreUser.findByEmail(userMail)

        if (preUser == null)
        {
            preUser = new PreUser(email: userMail, initialGroup: group)

            if (preUser.save(flush:true) == null)
            {
                log.error preUser.errors
                return -1
            }
        }
        else
        {
            preUser.dateCreated = new Date()
            if (preUser.save(flush:true) == null)
            {
                log.error preUser.errors
                return -1
            }
        }

        String link = grailsLinkGenerator.link(controller: 'userInvitation', action: 'index', params: [userMail : userMail], absolute: true)

        mailService.sendMail {
            to userMail
            from "johnDoe@g2one.com"
            subject "F1PorraVGA"
            body "Has sido invitado para unirte en el sistema de porras de VGAF1. Regístrate y juega accediendo a la siguiente URL:\n" + link + "\n\nEsta invitación dura 2 días. Si pasado ese tiempo no te registras, se descartará la invitación."
        }
    }



    def removeF1GPsSeason(int season)
    {
        def grandPrixes = GrandPrix.findAllBySeason(season)

        grandPrixes.each{gp->
             gp.delete(flush: true)
        }

        grandPrixes = GrandPrix.findAllBySeason(0)

        grandPrixes.each{gp->
            gp.delete(flush: true)
        }
    }


    def recordatoryToVote(GrandPrix gpOfVotes) {

        def usersToSendMail = User.findAllByMailNotifications(true)

        Date now = new Date()

        def hoursToFinishPole = gpOfVotes.finishPole.getTime() - now.getTime()
        hoursToFinishPole = hoursToFinishPole / (1000 * 3600)
        hoursToFinishPole = hoursToFinishPole.toInteger()


        for (User user : usersToSendMail)
        {
            if ((user.email == null) || (user.email == ""))
                continue;

            log.info "Enviando mail a " + user.email
            Billing userBilling = Billing.findByUserBillAndGrandPrix(user, gpOfVotes)

            if (userBilling == null)
            {
                mailService.sendMail {
                    to user.email
                    from "JohnDoe@f1porra.com"
                    subject "Recordatorio votación F1PorraVGA"
                    body "Quedan ${hoursToFinishPole} horas para el final del período de votación del GP de ${gpOfVotes.name}.\n" + "\n\nNo olvides votar en el sistema de porras VGA F1."
                }
            }
        }
    }






}
