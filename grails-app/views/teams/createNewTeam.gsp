<!DOCTYPE html>
<html>
<head>

    <meta name="layout" content="main"/>
    <title>VGA F1 Billings - Add New F1 Team</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootstrap.min.js')}"></script>

    <script type="text/javascript">
        $(document)
                .on('change', '.btn-file :file', function() {
                    var input = $(this),
                            numFiles = input.get(0).files ? input.get(0).files.length : 1,
                            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
                    input.trigger('fileselect', [numFiles, label]);
                });

        $(document).ready( function() {
            $('.btn-file :file').on('fileselect', function(event, numFiles, label) {

                var input = $(this).parents('.input-group').find(':text'),
                        log = numFiles > 1 ? numFiles + ' files selected' : label;

                if( input.length ) {
                    input.val(log);
                } else {
                    if( log ) alert(log);
                }

            });
        });







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
                        form.validate({

                            rules: {
                                password: "required",
                                password_again: {
                                    equalTo: "#password"
                                }
                            },

                            highlight: function(element) {
                                $(element).closest('.form-group').addClass('has-error');
                            },
                            unhighlight: function(element) {
                                $(element).closest('.form-group').removeClass('has-error');
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

    <g:if test="${message_error}">
        <div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>${message_error}</div>
    </g:if>

    <g:elseif test="${message}">
        <div class="alert alert-success"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>${message}</div>
    </g:elseif>


    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Create New Team</h3>
        </div>

        <g:form action="addTeam" class="validateForm" controller="teams" method="POST" enctype="multipart/form-data" name="teamForm">
        <div class="panel-body">
            <div class="row">

                <div class="col-lg-6 col-md-6">
                    <div class="form-group">
                        <label for="teamName">Team Name</label>
                        <a href="#" class="tooltipDemo" data-toggle="tooltip" title="F1 Team Name."></a>
                        <input class="form-control required_input" type="text" name="teamName" id="teamName" value="${newTeam?.name}">
                    </div>



                </div>
                <div class="col-lg-6 col-md-6">
                    <label>Image Team File</label>
                    <div class="input-group">

                        <span class="input-group-btn">
                            <span class="btn btn-primary btn-file">
                                Browse&hellip; <input type="file" name="imageFile" multiple>
                            </span>
                        </span>
                        <input type="text" class="form-control">
                    </div>
                </div>

            </div>

        </div>
        <div class="panel-footer">
            <button type="submit" class="btn btn-default" id="submitUser">Submit</button>
        </div>
        </g:form>
    </div>

</div>
</body>
</html>
