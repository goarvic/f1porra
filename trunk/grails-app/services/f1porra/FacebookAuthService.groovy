package f1porra

import com.the6hours.grails.springsecurity.facebook.FacebookAuthToken
import grails.transaction.Transactional
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.FacebookProfile
import org.springframework.social.facebook.api.impl.FacebookTemplate

@Transactional
class FacebookAuthService {

    User createAppUser(FacebookUser user, FacebookAuthToken token) {

        log.info "Login de usuario nuevo de tipo Facebook"

        Facebook facebook = new FacebookTemplate(token.accessToken.accessToken)
        FacebookProfile fbProfile = facebook.userOperations().userProfile

        log.info "Se procede a verificar si ya se encontraba registrado en el sistema mediante invitación"
        User appUser = User.findByEmail(fbProfile.email)

        if (appUser == null)
        {
            log.info "Usuario no presente en el sistema. Se crea uno nuevo"
            appUser = new User(username : fbProfile.email, name : fbProfile.name,
                            surname : fbProfile.lastName, email : fbProfile.email, password: user.uid)
        }

        user.setUser(appUser)
        if (appUser.save(flush:true) == null)
        {
            log.error "Error creando/actualizando a usuario " + appUser.errors
        }

        return appUser
    }
}
