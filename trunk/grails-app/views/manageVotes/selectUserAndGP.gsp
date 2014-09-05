

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>VGA F1 Billings - Manage votes</title>
    <meta name="layout" content="main"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <link rel="stylesheet" href="${resource(dir: 'css',file: 'datatable.css')}" />
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootbox.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/tables',file: 'datatable.js')}"></script>
</head>

<body>


    <script type="text/javascript">

        $(document).ready( function() {



            $(document).on( 'click', '.formUserDetails',
                    function()
                    {
                        $('#buttonSaveUserData').removeAttr("disabled", "disabled")
                    }
            );



        });

    </script>




    <div id="page-body" role="main" class="container">

        <%--<div class="jumbotron">
            <h1>Welcome to your Profile Section, <sec:username/></h1>
            <p>In this section you can see your clasification in the different groups to which you belong and invite new users to join a new group</p>
        </div>--%>

        <div id="userDetails">
            <g:form action="voteFormulary"  class="validateForm" controller="manageVotes" method="POST" enctype="multipart/form-data" name="teamForm">

                <div class="row">

                <div class="col-md-6">
                    <div class="form-group">
                        <label for="userId">Select User</label>
                        <div class="btn-group">
                            <select class="form-control" name="userId" id="userId">
                                <g:each in="${users}" var="user">
                                    <option value="${user.id}">${user.username}</option>
                                </g:each>

                            </select>
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label for="gpId">Select GP</label>
                        <div class="btn-group">
                            <select class="form-control" name="gpId" id="gpId">
                                <g:each in="${gpList}" var="grandPrix">
                                    <option value="${grandPrix.id}">${grandPrix.name}</option>
                                </g:each>
                            </select>
                        </div>
                    </div>
                </div>

                <button type="submit" class="btn btn-default" id="submitUserAndGP">Submit</button>

            </g:form>






            </div>
        </div>
    </div>
</body>
</html>