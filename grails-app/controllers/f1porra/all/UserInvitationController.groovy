package f1porra.all

import f1porra.GroupV
import f1porra.PreUser
import f1porra.Role
import f1porra.User
import f1porra.UserRole

class UserInvitationController {

    def index() {
        def userMail = params.userMail

        if (userMail == null)
        {
            request.error = "No invitation has been found for your email"
            render(view:"../index" , model: [])
        }

        else
        {
            PreUser userInvitation = PreUser.findByEmail(userMail)

            if (userInvitation == null)
            {
                request.error = "No invitation has been found for your email"
                render(view:"../index" , model: [])
            }
            else
            {
                Date dateExpiration = new Date()
                dateExpiration.setTime(dateExpiration.getTime()-172800000)

                if (userInvitation.dateCreated < dateExpiration)
                {
                    request.error = "The invitation has expired. Please, pay more attention next time"
                    render(view:"../index" , model: [])
                }
                else
                {
                   render(view: "createUserForm", model: [userInvitation : userInvitation])
                }
            }
        }
    }


    def addUser()
    {

        def userMail = params.userMail
        def username = params.username
        def password1 = params.password
        def password2 = params.password2
        def name = params.name
        def surname = params.surname

        if ((username == null) || (password1 == null) || (password2 == null) || (name == null) || (surname == null) || (userMail == null))
        {
            flash.error = "You must fill all fields"
            redirect(action: "index", controller: "userInvitation", params : [userMail : userMail])
        }

        PreUser userInvited = PreUser.findByEmail(userMail)

        if (userInvited == null)
        {
            redirect(action: "index", controller: "userInvitation", params : [userMail : userMail])
        }
        log.info "------------------------ " + userInvited.initialGroup.name
        Role roleUser = Role.findByAuthority("ROLE_USER")

        if (roleUser == null)
        {
            log.error "No se puede crear al usuario porque no se ha encontrado rol de usuario en el sistema"
            flash.error = "Internal Server Error. Try it later."
            redirect(action: "index", controller: "userInvitation", params : [userMail : userMail])
        }

        User newUser = new User(username : username, name : name, password: password1, email : userMail, surname: surname)
        GroupV initialGroup = userInvited.initialGroup

        if (newUser.save(flush:true) == null)
        {
            log.error "Error saving user: " + newUser.errors
            flash.error = "Internal Server Error. Try it later."
            redirect(action: "index", controller: "userInvitation", params : [userMail : userMail])
        }

        UserRole userRole = new UserRole(user: newUser, role: roleUser)

        if (userRole.save(flush:true) == null)
        {
            log.error "Error giving privileges to the user created " + userRole.errors
            flash.error = "Internal Server Error. Try it later."
            redirect(action: "index", controller: "userInvitation", params : [userMail : userMail])
        }
        else
        {

            initialGroup.addToUsers(newUser).save(flush:true)
            userInvited.delete(flush: true)
            request.message = "Congratulations! You are now part of the community. Now you can login with your username and password."
            render (view : "../index")
        }



    }


    def isUserNameAvailable()
    {

        def userName = params.username
        if (userName == null)
            render "false"

        User userAv = User.findByUsername(params.username)

        if (userAv == null)
            render "true"
        else
            render "false"

    }



}
