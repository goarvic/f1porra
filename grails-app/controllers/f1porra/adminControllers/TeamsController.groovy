package f1porra.adminControllers

import f1porra.f1.Team
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class TeamsController {
    def adminF1Service

    def index() {
        def teams = Team.list(sort:"name", order:"asc")

        render(view: "viewTeams", model : [teams : teams])
    }


    def teamAddForm()
    {
        render(view: "createNewTeam", model : [])
    }


    def addTeam()
    {
        if (!(request instanceof MultipartHttpServletRequest))
        {
            def message_error = "Error insertando datos"
            render(view: "viewTeams", model : [message_error : message_error])
            return
        }

        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
        CommonsMultipartFile imageFile = (CommonsMultipartFile) mpr.getFile("imageFile");


        if (imageFile.empty)
        {
            log.info "El fichero no existe!!"
            def message_error = "Error insertando datos"
            render(view: "viewTeams", model : [message_error : message_error])
            return
        }

        def teamName = params.teamName


        def imagesFolder = grailsApplication.config.grails.teams.images
        def dir = new File(imagesFolder)
        def nameFile = teamName + imageFile.getOriginalFilename()[imageFile.getOriginalFilename().size()-4 .. imageFile.getOriginalFilename().size()-1]

        if (dir.exists() && dir.isDirectory() && dir.canRead() && dir.canWrite())
        {
            imageFile.transferTo(new File(imagesFolder + nameFile))
        }
        else
        {
            def message_error = "Error salvando fichero de imagen"
            render(view: "viewTeams", model : [message_error : message_error])
            return
        }

        //Vamos a salvar los datos del equipo
        if (adminF1Service.saveNewTeam(teamName, nameFile) != 0)
        {
            def message_error = "Error insertando datos"
            render(view: "viewTeams", model : [message_error : message_error])
            return
        }

        render(view: "viewTeams", model : [])
    }
}
