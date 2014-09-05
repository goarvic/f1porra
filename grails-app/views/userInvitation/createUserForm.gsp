<!DOCTYPE html>
<html>
<head>

    <meta name="layout" content="main"/>
    <title>VGA F1 Billings - Create New User</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootstrap.min.js')}"></script>

    <script type="text/javascript">

    </script>



    <script type="text/javascript">
        $(document).ready
        (
                function ()
                {

                    // Para los tooltips de los titulos
                    $('.tooltipDemo').tooltip({
                        placement: 'right',
                        container: 'body',
                        delay: { show: 300, hide: 100 }
                    });

                    $('.validateForm').each(function(indice,valor) {
                        var form = $(this);

                        var urlValidateUsername = '${createLink(controller: "userInvitation", action: "isUserNameAvailable", absolute: true)}'

                        form.validate({

                            rules: {
                                username: {
                                    required: true,
                                    //username: true,
                                    remote: {
                                        url: urlValidateUsername,
                                        type: "post",
                                        data: {
                                            username: function() {
                                                console.log($("#username").val())
                                                return $("#username").val();
                                            }
                                        }
                                    }
                                },


                                password: "required",
                                password2: {
                                    equalTo: "#password"
                                }
                            },
                            messages: {
                                username: {
                                    required: "Enter a username",
                                    remote: "This username is not available"
                                }
                            },

                            highlight: function(element) {
                                $(element).closest('.form-group').addClass('has-error');
                                $('#submitUser').attr('disabled','disabled')
                            },
                            unhighlight: function(element) {
                                $(element).closest('.form-group').removeClass('has-error');
                                $('#submitUser').removeAttr("disabled");
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



                }
        );
    </script>




</head>
<body>
<div id="page-body" role="main" class="container">


    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">New User Formulary - ${userInvitation.email}</h3>
        </div>



        <g:form action="addUser"  class="validateForm" controller="userInvitation" method="POST" enctype="multipart/form-data" name="addUserForm">
        <div class="panel-body">
            <div class="row">
                <div class="col-lg-6 col-md-6">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <a href="#" class="tooltipDemo" data-toggle="tooltip" title="Username"></a>
                        <input class="form-control" name="username" id="username">
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <a href="#" class="tooltipDemo" data-toggle="tooltip" title="Password"></a>
                        <input class="form-control" name="password" type="password" id="password">
                    </div>

                    <div class="form-group">
                        <label for="password2">Password</label>
                        <a href="#" class="tooltipDemo" data-toggle="tooltip" title="Confirm Password"></a>
                        <input class="form-control" name="password2" type="password" id="password2">
                    </div>

                    <div class="form-group">
                        <label for="name">Name</label>
                        <a href="#" class="tooltipDemo" data-toggle="tooltip" title="Name"></a>
                        <input class="form-control" name="name" id="name">
                    </div>

                    <div class="form-group">
                        <label for="surname">Sur Name</label>
                        <a href="#" class="tooltipDemo" data-toggle="tooltip" title="Sur Name"></a>
                        <input class="form-control" name="surname" id="surname">
                    </div>

                    <input type="hidden" name="userMail" id="userMail" value="${userInvitation.email}" />

                </div>


                <div class="col-lg-6 col-md-6">
                    <div class="jumbotron">
                        <h1>Welcome to the VGA F1</h1>
                        <p>Please fill the Registration Form</p>
                        <p><a class="btn btn-primary btn-lg" role="button">Learn more</a></p>


                        </div>
                    </div>
                </div>





            </div>


            <div class="panel-footer">
                <button type="submit" class="btn btn-default" id="submitUser">Submit</button>
            </div>
        </div>
        </g:form>
    </div>

</div>
</body>
</html>
