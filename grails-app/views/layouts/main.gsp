<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="VGA Formula 1"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'miniLogo.png')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}" type="text/css">

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main-bootstrap.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-switch.css')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'styles.css')}" type="text/css">

    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.11.0.min.js')}"></script>

    <script src="${resource(dir: 'js', file: 'bootstrap-switch.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootstrap.js')}"></script>


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->

    <g:layoutHead/>

    <script type="text/javascript">

        $(document).ready(function () {

            var pathname = window.location.pathname;
            $('.active').removeClass('active');


            if (pathname.indexOf("teams")>0)
                $("#manageTeamsMenu").addClass("active");
            else if (pathname.indexOf("drivers")>0)
                $("#manageDriversMenu").addClass("active");
            else if (pathname.indexOf("profile")>0)
                $("#profileUser").addClass("active");
            else if (pathname.indexOf("manageGrandPrix")>0)
                $("#manageGPsMenu").addClass("active");
            else if (pathname.indexOf("clasification")>0)
                $("#clasificationMenu").addClass("active");
            else if (pathname.indexOf("billings")>0)
                $("#billingsMenu").addClass("active");
            else if (pathname.indexOf("rules")>0)
                $("#rulesMenu").addClass("active");



            <sec:ifLoggedIn>
            var urlUserInvitations = "${createLink(controller: "userInfo", action: "getNewInvitationsGroup")}"
            var urlAllInvitations = "${createLink(controller: "userInfo", action: "getInvitations")}"
            var data
            $.ajax({
                url: urlUserInvitations,
                dataType: 'json',
                success: function(response) {
                    data = response;
                    console.log(data);
                    if (data.length > 0)
                    {
                        $("#infoInvitations").html("<span class='glyphicon glyphicon-globe' style='margin-right:10px;'></span><g:message code="default.layouts.main.invitationsMessage"/>");
                        $('#infoInvitations').show();
                    }
                },
                error: function() {
                    console.log("Error request API");
                }
            });


            $.ajax({
                url: urlAllInvitations,
                dataType: 'json',
                success: function(response) {
                    data = response;
                    console.log(data);
                    if (data.length > 0)
                    {
                        $("#invitationsLabel").html(data.length);

                        $('#iconUser').hide();
                        $('#invitationsLabel').show();
                    }
                },
                error: function() {
                    console.log("Error request API");
                }
            });

            </sec:ifLoggedIn>

        });

    </script>


</head>
<body>


<!-- Fixed navbar -->
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand" href="#"><span class="textPresentation"></span></a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">

                    <sec:ifAllGranted roles="ROLE_ADMIN">
                        <li id="manageDriversMenu" class="active"><a href="${createLink(action: 'index', controller: 'drivers')}"><span class="navmenu glyphicon glyphicon-fire"></span>Drivers</a></li>
                        <li id="manageTeamsMenu" class="active"><a href="${createLink(action: 'index', controller: 'teams')}"><span class="navmenu glyphicon glyphicon-th-large"></span>Teams</a></li>
                        <li id="manageGPsMenu" class="active"><a href="${createLink(action: 'index', controller: 'manageGrandPrix')}"><span class="glyphicon glyphicon-calendar"></span>GP's</a></li>
                    </sec:ifAllGranted>
                    <sec:ifAnyGranted roles="ROLE_USER, ROLE_FACEBOOK">
                        <li id="billingsMenu" class="active"><a href="${createLink(action: 'index', controller: 'billings')}"><span class="navmenu glyphicon glyphicon-play"></span><g:message code="default.layouts.main.letsPlayMenu"/></a></li>
                        <li id="clasificationMenu" class="active"><a href="${createLink(action: 'index', controller: 'clasification')}"><span class="navmenu  glyphicon glyphicon-list-alt"></span><g:message code="default.layouts.main.classificationMenu"/></a></li>
                        <li id="rulesMenu" class="active"><a href="${createLink(action: 'index', controller: 'rulesInfo')}"><span class="navmenu glyphicon glyphicon-flag"></span><g:message code="default.layouts.main.rulesMenu"/></a></li>
                    </sec:ifAnyGranted>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <sec:ifLoggedIn>

                    <li id="profileUser" class="active"><a href="${createLink(action: 'index', controller: 'profile')}"><span id="invitationsLabel" class="label label-info" style="display:none;"></span><span id="iconUser" class="navmenu glyphicon glyphicon-user"></span><sec:username/></a></li>
                    <li id="logout" class="active"><a href="${createLink(action: 'index', controller: 'logout')}"><span class="navmenu glyphicon glyphicon-log-out"></span><g:message code="default.layouts.main.logoutMenu"/></a></li>

                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <li id="logout" class="active"><a href="${createLink(action: 'index', controller: 'login')}"><span class="navmenu glyphicon glyphicon-log-in"></span><g:message code="default.layouts.main.loginMenu"/></a></li>
                    <%--<li id="reg" class="active"><a href="${createLink(action: 'index', controller: 'login')}"><span class="navmenu glyphicon glyphicon-globe"></span><g:message code="default.layouts.main.registerMenu"/></a></li>--%>

                </sec:ifNotLoggedIn>
            </ul>

        </div><!--/.nav-collapse -->

    </div>
</div>


<g:if test="${request.error}">
    <div id="page-body" role="main" class="container">
        <div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span class="glyphicon glyphicon-remove" style="margin-right: 15px;"></span> ${request.error}</div>
    </div>
</g:if>
<g:elseif test="${flash.error}">
    <div id="page-body" role="main" class="container">
        <div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span class="glyphicon glyphicon-remove" style="margin-right: 15px;"></span> ${flash.error}</div>
    </div>
</g:elseif>
<g:elseif test="${request.message}">
    <div id="page-body" role="main" class="container">
        <div class="alert alert-success"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span class="glyphicon glyphicon-ok" style="margin-right: 15px;"></span> ${request.message}</div>
    </div>
</g:elseif>
<g:elseif test="${flash.message}">
    <div id="page-body" role="main" class="container">
        <div class="alert alert-success"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span class="glyphicon glyphicon-ok" style="margin-right: 15px;"></span> ${flash.message}</div>
    </div>
</g:elseif>


<div id="page-body" role="main" class="container">
    <sec:ifLoggedIn>
        <div id="infoInvitations" class="alert alert-info" style="display: none;"></div>
    </sec:ifLoggedIn>
</div>

<g:layoutBody/>

</body>
</html>
