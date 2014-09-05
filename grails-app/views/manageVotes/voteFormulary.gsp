<!DOCTYPE html>
<html>
<head>

    <meta name="layout" content="main"/>
    <title>VGA F1 Billings - Vote</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootstrap.min.js')}"></script>

    <script type="text/javascript">

        var qualyDriversSelected = [null , null , null , null , null , null, null, null]
        var raceDriversSelected = [null , null , null , null , null , null, null, null]





        $(document).on( 'change', '.raceSelect', function() {

            //console.log(this.id + "->" + this.value);

            var idOfSelect = this.id
            if (idOfSelect[1] != '0')
            {
                positionOfArray = idOfSelect[0]
            }
            else
            {
                positionOfArray = idOfSelect[0] + idOfSelect[1]
            }

            positionOfArray = parseInt(positionOfArray)


            if (raceDriversSelected[positionOfArray-1] != null)
                $(".raceSelect option[value=" + raceDriversSelected[positionOfArray-1] + "]").removeAttr('disabled');



            //Hay que añadir al piloto desseleccionado al resto de selects

            for (var i= 1; i<9; i++)
            {
                if (i != positionOfArray)
                {
                    var idOfSelectToDisableAtt = "#" +  i + "pos_race"
                    //console.log(idOfSelectToDisableAtt + " option[value=" + this.value + "]")
                    $(idOfSelectToDisableAtt + " option[value=" + this.value + "]").attr('disabled','disabled')
                }
            }

            var txt = $("#" + this.id + " option:selected").text();

            raceDriversSelected[positionOfArray-1] = this.value
        });



        $(document).on( 'change', '.qualySelect', function() {

            //console.log(this.id + "->" + this.value);

            var idOfSelect = this.id
            if (idOfSelect[1] != '0')
            {
                positionOfArray = idOfSelect[0]
            }
            else
            {
                positionOfArray = idOfSelect[0] + idOfSelect[1]
            }

            positionOfArray = parseInt(positionOfArray)


            if (qualyDriversSelected[positionOfArray-1] != null)
                $(".qualySelect option[value=" + qualyDriversSelected[positionOfArray-1] + "]").removeAttr('disabled');



            //Hay que añadir al piloto desseleccionado al resto de selects

            for (var i= 1; i<9; i++)
            {
                if (i != positionOfArray)
                {

                    var idOfSelectToDisableAtt = "#" +  i + "pos_qualy"
                    //console.log(idOfSelectToDisableAtt + " option[value=" + this.value + "]")
                    $(idOfSelectToDisableAtt + " option[value=" + this.value + "]").attr('disabled','disabled')

                }
            }

            var txt = $("#" + this.id + " option:selected").text();

            qualyDriversSelected[positionOfArray-1] = this.value


        });




        $(document)
                .on('change', '.btn-file :file', function() {
                    var input = $(this),
                            numFiles = input.get(0).files ? input.get(0).files.length : 1,
                            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
                    input.trigger('fileselect', [numFiles, label]);
                });

        $(document).on( 'click', '#raceButtonForm',
                function(){
                    $('#btn-primary').removeClass("#btn-primary")
                    var buttonQualy = document.getElementById("qualyButtonForm")
                    var fastLapButton = document.getElementById("fastLapButtonForm")

                    buttonQualy.setAttribute("class", "btn btn-default")
                    fastLapButton.setAttribute("class", "btn btn-default")

                    $('#qualyForm').hide()
                    $('#fastLapForm').hide()
                    $('#raceForm').show()


                    var buttonRace = document.getElementById("raceButtonForm")
                    buttonRace.setAttribute("class", "btn btn-primary")

                }
        );


        $(document).on( 'click', '#qualyButtonForm',
                function(){

                    $('#btn-primary').removeClass("#btn-primary")
                    var buttonRace = document.getElementById("raceButtonForm")
                    var fastLapButton = document.getElementById("fastLapButtonForm")

                    buttonRace.setAttribute("class", "btn btn-default")
                    fastLapButton.setAttribute("class", "btn btn-default")

                    $('#raceForm').hide()
                    $('#fastLapForm').hide()
                    $('#qualyForm').show()

                    var buttonQualy = document.getElementById("qualyButtonForm")
                    buttonQualy.setAttribute("class", "btn btn-primary")

                }
        );


        $(document).on( 'click', '#fastLapButtonForm',
                function(){

                    $('#btn-primary').removeClass("#btn-primary")
                    var buttonRace = document.getElementById("raceButtonForm")
                    var buttonQualy = document.getElementById("qualyButtonForm")


                    buttonRace.setAttribute("class", "btn btn-default")
                    buttonQualy.setAttribute("class", "btn btn-default")

                    $('#raceForm').hide()
                    $('#qualyForm').hide()
                    $('#fastLapForm').show()

                    var fastLapButton = document.getElementById("fastLapButtonForm")
                    fastLapButton.setAttribute("class", "btn btn-primary")
                }
        );

        $(document).ready( function() {

            <g:if test="${userBilling != null}">
                qualyDriversSelected = [${userBilling.qualyBill.pos1.id},${userBilling.qualyBill.pos2.id},${userBilling.qualyBill.pos3.id},${userBilling.qualyBill.pos4.id},${userBilling.qualyBill.pos5.id},${userBilling.qualyBill.pos6.id},${userBilling.qualyBill.pos7.id},${userBilling.qualyBill.pos8.id}]
                raceDriversSelected = [${userBilling.raceBill.pos1.id},${userBilling.raceBill.pos2.id},${userBilling.raceBill.pos3.id},${userBilling.raceBill.pos4.id},${userBilling.raceBill.pos5.id},${userBilling.raceBill.pos6.id},${userBilling.raceBill.pos7.id},${userBilling.raceBill.pos8.id}]

                for (var i=0; i<9; i++)
                {
                    for (var j=1; j<=8; j++)
                    {
                        if (i+1!=j)
                        {
                            var idOfSelectToDisableAtt = "#" +  j + "pos_qualy"
                            $(idOfSelectToDisableAtt + " option[value=" + qualyDriversSelected[i] + "]").attr('disabled','disabled')
                            idOfSelectToDisableAtt = "#" +  j + "pos_race"
                            $(idOfSelectToDisableAtt + " option[value=" + raceDriversSelected[i] + "]").attr('disabled','disabled')
                        }
                    }
                }
            </g:if>


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



    </script>

</head>
<body>
<div id="page-body" role="main" class="container">


    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Vote GP Results</h3>
        </div>



        <g:form action="vote"  class="validateForm" controller="manageVotes" method="POST" enctype="multipart/form-data" name="teamForm">

        <div class="panel-body">
            <input type="hidden" name="userId" id="userId" value="${actualUser.id}">
            <input type="hidden" name="gpId" id="gpId" value="${actualGP.id}">
            <div class="btn-group btn-group-lg buttonGroup">
                <button class="btn btn-primary" type="button" id="qualyButtonForm">Qualify</button>
                <button class="btn btn-default" type="button" id="raceButtonForm">Race</button>
                <button class="btn btn-default" type="button" id="fastLapButtonForm">Fast Lap</button>
            </div>
            <div class="row">

                <div id="qualyForm">


                    <div class="col-lg-6 col-md-6">

                        <g:each var="i" in="${ (1..<9) }">
                            <div class="form-group">
                                <label for="${i}pos_qualy">Qualy - Position ${i}</label>
                                <a href="#" class="tooltipDemo" data-toggle="tooltip" title="Choose Driver for Position ${i} in qualy"></a>
                                <select class="form-control qualySelect" name="pos${i}_qualy" id="${i}pos_qualy">
                                    <g:if test="${userBilling == null}">
                                        <option value="null" selected="selected">--</option>
                                    </g:if>
                                    <g:else>
                                        <g:if test="${(i==1)}">
                                            <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos1.id}"/>
                                        </g:if>
                                        <g:elseif test="${i == 2}">
                                            <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos2.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 3}">
                                            <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos3.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 4}">
                                            <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos4.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 5}">
                                            <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos5.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 6}">
                                            <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos6.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 7}">
                                            <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos7.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 8}">
                                            <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos8.id}"/>
                                        </g:elseif>


                                    </g:else>
                                    <g:each in="${driversList}" var="driver">
                                        <g:if test="${((userBilling != null) && (driverIdSelected == driver.id))}">
                                            <option value="${driver.id}" selected="selected">${driver.name} - ${driver.team.name}</option>
                                        </g:if>
                                        <g:else>
                                            <option value="${driver.id}">${driver.name} - ${driver.team.name}</option>
                                        </g:else>
                                    </g:each>
                                </select>
                            </div>
                        </g:each>
                    </div>
                </div>

                <div id="raceForm" style="display:none;">


                    <div class="col-lg-6 col-md-6">

                        <g:each var="i" in="${ (1..<9) }">
                            <div class="form-group">
                                <label for="${i}pos_race">Race - Position ${i}</label>
                                <a href="#" class="tooltipDemo" data-toggle="tooltip" title="Choose Driver for Position ${i} in race"></a>
                                <select class="form-control raceSelect" name="pos${i}_race" id="${i}pos_race">
                                    <g:if test="${userBilling == null}">
                                        <option value="null" selected="selected">--</option>
                                    </g:if>
                                    <g:else>
                                        <g:if test="${(i==1)}">
                                            <g:set var="driverIdSelected" value="${userBilling.raceBill.pos1.id}"/>
                                        </g:if>
                                        <g:elseif test="${i == 2}">
                                            <g:set var="driverIdSelected" value="${userBilling.raceBill.pos2.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 3}">
                                            <g:set var="driverIdSelected" value="${userBilling.raceBill.pos3.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 4}">
                                            <g:set var="driverIdSelected" value="${userBilling.raceBill.pos4.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 5}">
                                            <g:set var="driverIdSelected" value="${userBilling.raceBill.pos5.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 6}">
                                            <g:set var="driverIdSelected" value="${userBilling.raceBill.pos6.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 7}">
                                            <g:set var="driverIdSelected" value="${userBilling.raceBill.pos7.id}"/>
                                        </g:elseif>
                                        <g:elseif test="${i == 8}">
                                            <g:set var="driverIdSelected" value="${userBilling.raceBill.pos8.id}"/>
                                        </g:elseif>
                                    </g:else>
                                    <g:each in="${driversList}" var="driver">
                                        <g:if test="${((userBilling != null) && (driverIdSelected == driver.id))}">
                                            <option value="${driver.id}" selected="selected">${driver.name} - ${driver.team.name}</option>
                                        </g:if>
                                        <g:else>
                                            <option value="${driver.id}">${driver.name} - ${driver.team.name}</option>
                                        </g:else>
                                    </g:each>
                                </select>
                            </div>
                        </g:each>
                    </div>
                </div>




                <div id="fastLapForm" style="display:none;">


                    <div class="col-lg-6 col-md-6">


                            <div class="form-group">
                                <label for="fastLap">Fast Lap</label>
                                <a href="#" class="tooltipDemo" data-toggle="tooltip" title="Choose Driver for Fast Lap"></a>
                                <select class="form-control" name="fastLap" id="fastLap">
                                    <g:if test="${userBilling == null}">
                                        <option value="null" selected="selected">--</option>
                                    </g:if>
                                    <g:each in="${driversList}" var="driver">
                                        <g:if test="${((userBilling != null) && (userBilling.fastLapBill.id == driver.id))}">
                                            <option value="${driver.id}" selected="selected">${driver.name} - ${driver.team.name}</option>
                                        </g:if>
                                        <g:else>
                                            <option value="${driver.id}">${driver.name} - ${driver.team.name}</option>
                                        </g:else>
                                    </g:each>
                                </select>
                            </div>

                    </div>
                </div>




                    <div class="col-lg-6 col-md-6">
                        <div class="jumbotron">
                            <div class="summaryRace">
                                <h1>${actualGP.name}</h1>
                                <h2>Race Summary</h2>
                                <div class="summaryRaceRow row">
                                    <div class="col-md-6">
                                        <span class="glyphicon glyphicon-flag"></span>
                                        Free Practice Start:
                                    </div>
                                    <div class="col-md-6">
                                        <g:formatDate format="yyyy-MM-dd HH:mm" date="${actualGP.freePracticeStart}"/>
                                    </div>
                                </div>
                                <div class="summaryRaceRow row">
                                    <div class="col-md-6">
                                        <span class="glyphicon glyphicon-flag"></span>
                                        Clasification Start:
                                    </div>
                                    <div class="col-md-6">
                                        <g:formatDate format="yyyy-MM-dd HH:mm" date="${actualGP.qualyStart}"/>
                                    </div>
                                </div>
                                <div class="summaryRaceRow row">
                                    <div class="col-md-6">
                                        <span class="glyphicon glyphicon-flag"></span>
                                        Race Start:
                                    </div>
                                    <div class="col-md-6">
                                        <g:formatDate format="yyyy-MM-dd HH:mm" date="${actualGP.raceStart}"/>
                                    </div>
                                </div>
                                <div class="summaryRaceRow row">
                                    <div class="col-md-6">
                                        <span class="glyphicon glyphicon-flag"></span>
                                        Finish Vote Period:
                                    </div>
                                    <div class="col-md-6">
                                        <g:formatDate format="yyyy-MM-dd HH:mm" date="${actualGP.finishPole}"/>
                                    </div>
                                </div>

                            </div>

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
