class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")

        "/getInvitationsUser"(controller: "userInfo", action: "getNewInvitationsGroup")

        "500"(view:'/error')
	}
}
