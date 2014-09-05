

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>VGA F1 Billings - Profile</title>
    <meta name="layout" content="main"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <link rel="stylesheet" href="${resource(dir: 'css',file: 'datatable.css')}" />
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootbox.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/tables',file: 'datatable.js')}"></script>
</head>

<body>


    <script type="text/javascript">

        var groupSelected = null;



        $(document).ready( function() {

            $("#myGroups").addClass("active");

            $("[name='emailNotificationsCheckbox']").bootstrapSwitch();


            $('.validateGroupForm').each(function(indice,valor) {
                var form = $(this);

                form.validate({

                    rules: {
                        groupName: {
                            required: true//,
                        }
                    },
                    messages: {
                        groupName: {
                            required: "Enter a Group Name"
                        }
                    },

                    highlight: function(element) {
                        $(element).closest('.form-group').addClass('has-error');
                        $('#createGroupButton').attr('disabled','disabled')
                    },
                    unhighlight: function(element) {
                        $(element).closest('.form-group').removeClass('has-error');
                        $('#createGroupButton').removeAttr("disabled");
                    },
                    errorElement: 'span',
                    errorClass: 'help-block',
                    errorPlacement: function(error, element) {
                        if(element.parent('.input-group').length) {
                            error.insertAfter(element.parent());
                        } else {
                            error.insertAfter(element);
                        }
                    }
                });
            });

            jQuery.validator.addClassRules("required_input", {
                required: true
            });

        });









        /*$(document).on( 'click', '#buttonSendInvitation', function () {


            var email = $("#userMail").val()
            var urlUserInvitations = '${createLink(controller: "profile", action: "ajaxInviteUserToGroup")}'

            urlUserInvitations += "?groupName=" + groupSelected + "&userMail=" + email


            $.ajax({
                url: urlUserInvitations,
                dataType: 'json',
                success: function(response) {
                    data = response;
                    console.log(data);

                },
                error: function() {
                    console.log("Error request API");
                }
            });
        });*/









        $(document).on( 'click', '#buttonSaveUserData', function () {
            var btn = $(this)
            btn.button('loading')

            var newMail = $("#userEMail").val()
            var emailNotificationsCheckbox = $("[name='emailNotificationsCheckbox']").bootstrapSwitch('state')
            var displayName = $("#displayName").val()


            var url = '<g:createLink controller="profile" action="modifyUserData"/>'

            var requestObj = {
                userMail: newMail,
                emailNotifications: emailNotificationsCheckbox,
                displayName : displayName
            };

            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(requestObj),
                success:
                    function(response)
                    {
                        if (response.status == "success")
                        {
                            btn.button('reset')
                            btn.attr("disabled", "disabled")
                        }
                        else
                        {
                            btn.button('error')
                        }
                    }
            })
        });


        $(document).on( 'click', '.groupInviteButton',
                function()
                {
                    groupSelected = this.id.substr(12,this.id.length-1)
                }
        );


        $(document).on( 'click', '.groupInviteButton',
                function()
                {
                    $('#inviteFormulary').show();

                    window.location.assign("#inviteDialog")


                    var groupName = $(this).parent().attr("id");
                    groupName = groupName.substr(6,groupName.length-1);


                    var inputGroupName = document.getElementById('groupNameInput');

                    $('#groupNameInput').val(groupName);
                    console.log($('#groupNameInput').val())


                }
        );



        $(document).on( 'click', '.formUserDetails',
                function()
                {
                    $('#buttonSaveUserData').removeAttr("disabled", "disabled")
                }
        );


        $("[name='emailNotificationsCheckbox']").on('switchChange.bootstrapSwitch', function () {



            $('#buttonSaveUserData').removeAttr("disabled", "disabled")



        });





        $(document).on( 'click', '#myInvitations',
                function()
                {
                    $('#groupsSection').hide();
                    $('#invitationsSection').show();

                    $('#myGroups').removeClass('active');
                    $("#myInvitations").addClass("active");

                }
        );

        $(document).on( 'click', '#myGroups',
                function()
                {
                    $('#invitationsSection').hide();
                    $('#groupsSection').show();

                    $('#myInvitations').removeClass('active');
                    $("#myGroups").addClass("active");

                }
        );

        $(document).on( 'click', '.groupEraseButton',
                function()
                {
                    var groupId = $(this).attr("id").substr(12)

                    var link = '${createLink(controller: "profile", action: "deleteGroup")}?groupId=' + groupId

                    if (confirm('<g:message code="default.profile.confirmErase"/>'))
                        window.location=link
                }
        );

        $(document).on( 'click', '.groupLeaveButton',
                function()
                {
                    var groupId = $(this).attr("id").substr(12)

                    var link = '${createLink(controller: "profile", action: "leaveGroup")}?groupId=' + groupId

                    if (confirm('<g:message code="default.profile.confirmLeave"/>'))
                        window.location=link
                }
        );






    </script>




    <div id="page-body" role="main" class="container">

        <%--<div class="jumbotron">
            <h1>Welcome to your Profile Section, <sec:username/></h1>
            <p>In this section you can see your clasification in the different groups to which you belong and invite new users to join a new group</p>
        </div>--%>

        <div id="userDetails">
            <div class="row">

                <div class="col-md-4">
                    <div class="form-group">
                        <label for="emailNotificationsCheckbox"><g:message code="default.profile.emailNotificationsLabel"/></label>
                        <g:if test="${actualUser.mailNotifications == true}">
                            <input class="formUserDetails" type="checkbox" id="emailNotificationsCheckbox" name="emailNotificationsCheckbox" checked>
                        </g:if>
                        <g:else>
                            <input class="formUserDetails" type="checkbox" id="emailNotificationsCheckbox" name="emailNotificationsCheckbox">
                        </g:else>

                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label for="displayName"><g:message code="default.profile.displayName"/></label>
                        <input class="formUserDetails" type="text" id="displayName" name="displayName" value="${actualUser.name}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label for="userEMail"><g:message code="default.profile.emailAddress"/></label>
                        <input class="formUserDetails" id="userEMail" type="text" name="userEMail" value="${actualUser.email}">
                    </div>
                </div>



            </div>

            <div class="row">
                <button type="button" id="buttonSaveUserData" disabled="disabled" data-loading-text="<g:message code="default.profile.savingButton"/>" class="btn btn-primary">
                    <g:message code="default.profile.saveButton"/>
                </button>
            </div>
        </div>

        <ul class="nav nav-pills">
            <li id="myGroups"><a href="#"><g:message code="default.profile.myGroups"/></a></li>

            <g:if test="${actualUser.invitations.size() > 0}">
                <li id="myInvitations"><a href="#"><g:message code="default.profile.invitations"/> <span class="label label-info">${actualUser.invitations.size()}</span></a></li>
            </g:if>
            <g:else>
                <li id="myInvitations"><a href="#"><g:message code="default.profile.invitations"/></a></li>
            </g:else>

        </ul>


        <div id="groupsSection">
            <div class="page-header">
                <%--<h1><g:message code="default.profile.myGroups"/></h1>--%>
            </div>

            <%--<div id="inviteFormulary" class="panel panel-default" style="display:none;">

                <div class="panel-heading">
                    <h3 class="panel-title">Invite user to group</h3>
                </div>
                <div class="panel-body">
                    <g:form action="inviteUserToGroup"  class="validateForm" controller="profile" method="POST" enctype="multipart/form-data" name="addUserForm" id="addUserForm">


                        <div class="form-group">
                            <div class="col-md-2">
                                <label for="userMail">User mail</label>
                            </div>


                            <div class="col-md-9">
                                <input class="form-control" name="userMail" id="userMail">
                            </div>
                            <div class="col-md-1">
                                <button type="submit" class="btn btn-default" id="submitUser">Submit</button>
                            </div>
                        </div>
                        <input type="hidden" name="groupName" id="groupNameInput" value="hola" />

                    </g:form>
                </div>



            </div>--%>


            <g:set var="iteratorGroups" value="${1}" />

            <div class="row">
                <g:each in="${actualUser.groupsInside}" var="group">
                    <g:if test="${((iteratorGroups % 4) == 1) && (iteratorGroups > 1)}">
                        </div>
                        <div class="row">
                    </g:if>


                    <div class="col-md-3">
                        <div id="group_${group.name}" class="group">
                            <g:if test="${group.name.size() > 12}">
                                <g:set var="sortedNameGroup" value="${new String(group.name[0..9] + '...')}"/>
                                <h1>${sortedNameGroup}</h1>
                            </g:if>
                            <g:else>
                                <h1>${group.name}</h1>
                            </g:else>
                            <g:if test="${group.owner.id != actualUser.id}">
                                <%--<a href="${createLink(controller: "profile", action: "leaveGroup")}?groupId=${group.id}">
                                    <span class="label label-primary"><span class="glyphicon glyphicon-share"></span> <g:message code="default.profile.leaveGroup"/></span>
                                </a>--%>
                                <button id="buttonLeave_${group.id}" class="btn btn-primary btn-sm groupLeaveButton">
                                    <span class="label label-primary"><span class="glyphicon glyphicon-share"></span> <g:message code="default.profile.leaveGroup"/>
                                </button>
                            </g:if>
                            <g:else>
                                <%--<a href="${createLink(controller: "profile", action: "deleteGroup")}?groupId=${group.id}">
                                    <span class="label label-danger"><span class="glyphicon glyphicon-remove"></span> <g:message code="default.profile.remove"/></span>
                                </a>--%>
                                <button id="buttonErase_${group.id}" class="btn btn-danger btn-sm groupEraseButton">
                                    <span class="label label-danger"><span class="glyphicon glyphicon-remove"></span> <g:message code="default.profile.remove"/>
                                </button>


                                <%--<a data-target="#myModal" class="inviteUser"><span class="label label-primary"><span class="glyphicon glyphicon-user"></span> <g:message code="default.profile.invite"/></span></a>--%>

                                <button id="buttonInvite_${group.name}" class="btn btn-primary btn-sm groupInviteButton" data-toggle="modal" data-target="#myModal">
                                    <span class="label label-primary"><span class="glyphicon glyphicon-user"></span> <g:message code="default.profile.invite"/>
                                </button>

                            </g:else>
                            <span style="display: block; margin-top:14px;">${group.users.size()} players</span>
                        </div>

                    </div>
                    <g:set var="iteratorGroups" value="${iteratorGroups + 1}" />
                </g:each>

                <div class="col-md-3">
                    <div class="group">
                        <g:form controller="profile" action="createGroup" method="POST" enctype="multipart/form-data" name="addGroupForm" id="addGroupForm" class="validateGroupForm">
                            <h1><button id="createGroupButton" type="submit"><span class="glyphicon glyphicon-plus"/></button></h1>
                            <div class="input-group input-group-sm" style="margin: auto">
                                <input type="text" class="form-control" placeholder="Group Name" name="groupName">
                            </div>
                        </g:form>



                    </div>
                </div>


            </div>
        </div>

        <div id="invitationsSection" style="display:none;">

            <g:set var="iteratorInvitations" value="${1}" />


            <div class="page-header">
                <%--<h1>Invitations to Groups</h1>--%>
            </div>


            <div class="row">
                <g:each in="${actualUser.invitations}" var="invitation">
                    <g:if test="${((iteratorInvitations % 4) == 1) && (iteratorInvitations > 1)}">
                        </div>
                        <div class="row">
                    </g:if>


                    <div class="col-md-3">
                        <div id="invitations_${invitation.groupInvitation.name}" class="group">
                            <h1>${invitation.groupInvitation.name}</h1>
                            <a href="${createLink(controller: "profile", action: "acceptInvitation")}?invitationId=${invitation.id}"><span class="label label-success"><span class="glyphicon glyphicon-ok"></span> Accept</span></a>
                            <a href="${createLink(controller: "profile", action: "declineInvitation")}?invitationId=${invitation.id}"><span class="label label-danger"><span class="glyphicon glyphicon-remove"></span> Decline</span></a>

                        </div>

                    </div>
                    <g:set var="iteratorGroups" value="${iteratorGroups + 1}" />
                </g:each>

            </div>


        </div>








    <div class="modal fade" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <g:form action="inviteUserToGroup"  class="validateForm" controller="profile" method="POST" enctype="multipart/form-data" name="addUserForm" id="addUserForm">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Modal title</h4>
                    </div>
                    <div class="modal-body">



                            <div class="form-group">
                                <div class="col-md-2">
                                    <label for="userMail">User mail</label>
                                </div>


                                <div class="col-md-9">
                                    <input class="form-control" name="userMail" id="userMail">
                                </div>

                            </div>

                        <input type="hidden" name="groupName" id="groupNameInput" value="hola" />

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button id="buttonSendInvitation" type="submit" type="button" class="btn btn-primary">Send Invitation</button>
                    </div>
                </g:form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->










    </div>
</body>
</html>