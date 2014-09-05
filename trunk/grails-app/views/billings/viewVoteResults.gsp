<!DOCTYPE html>
<html>
<head>

    <meta name="layout" content="main"/>
    <title><g:message code="default.billings.results"/> - ${userBilling.userBill.name}</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootstrap.min.js')}"></script>

    <script type="text/javascript">


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
            </g:if>

        });



    </script>

</head>
<body>
<div id="page-body" role="main" class="container">


    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><g:message code="default.billings.results"/> - <b> ${userBilling.userBill.name}</b></h3>
        </div>

        <div class="panel-body">
            <input type="hidden" name="gpId" id="gpId" value="${actualGP.id}">
            <div class="btn-group btn-group-lg buttonGroup">
                <button class="btn btn-primary" type="button" id="qualyButtonForm"><g:message code="default.billings.qualy"/></button>
                <button class="btn btn-default" type="button" id="raceButtonForm"><g:message code="default.billings.race"/></button>
                <button class="btn btn-default" type="button" id="fastLapButtonForm"><g:message code="default.billings.fastLap"/></button>
            </div>

            <g:if test="${userBilling != null}">
            <div class="row">

            <div id="qualyForm">
                <g:set var="hitsQualyCounter" value="${0}"/>
                <g:set var="hitsRaceCounter" value="${0}"/>
                <g:set var="hitFastLapCounter" value="${0}"/>

                <div class="col-lg-4 col-md-4">


                    <g:each var="i" in="${ (1..<11) }">
                        <div class="form-group">
                            <label for="${i}pos_qualy">Qualy - Position ${i} - </label>



                            <g:if test="${(i==1)}">
                                <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos1.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.qualyBill.pos1.name}"/>
                                <g:if test="${actualGP.qualyResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.qualyResult.pos1.name}"/>
                                </g:if>
                            </g:if>
                            <g:elseif test="${i == 2}">
                                <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos2.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.qualyBill.pos2.name}"/>
                                <g:if test="${actualGP.qualyResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.qualyResult.pos2.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 3}">
                                <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos3.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.qualyBill.pos3.name}"/>
                                <g:if test="${actualGP.qualyResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.qualyResult.pos3.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 4}">
                                <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos4.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.qualyBill.pos4.name}"/>
                                <g:if test="${actualGP.qualyResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.qualyResult.pos4.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 5}">
                                <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos5.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.qualyBill.pos5.name}"/>
                                <g:if test="${actualGP.qualyResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.qualyResult.pos5.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 6}">
                                <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos6.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.qualyBill.pos6.name}"/>
                                <g:if test="${actualGP.qualyResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.qualyResult.pos6.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 7}">
                                <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos7.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.qualyBill.pos7.name}"/>
                                <g:if test="${actualGP.qualyResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.qualyResult.pos7.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 8}">
                                <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos8.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.qualyBill.pos8.name}"/>
                                <g:if test="${actualGP.qualyResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.qualyResult.pos8.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 9}">
                                <g:if test="${userBilling.qualyBill.pos9 != null}">
                                    <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos9.id}"/>
                                    <g:set var="driverNameSelected" value="${userBilling.qualyBill.pos9.name}"/>
                                    <g:if test="${actualGP.qualyResult != null}">
                                        <g:set var="driverNameResult" value="${actualGP.qualyResult.pos9.name}"/>
                                    </g:if>
                                </g:if>

                                <g:else>
                                    <g:set var="driverIdSelected" value="-1"/>
                                    <g:set var="driverNameSelected" value="No selection"/>

                                </g:else>
                            </g:elseif>
                            <g:elseif test="${i == 10}">
                                <g:if test="${userBilling.qualyBill.pos10 != null}">
                                    <g:set var="driverIdSelected" value="${userBilling.qualyBill.pos10.id}"/>
                                    <g:set var="driverNameSelected" value="${userBilling.qualyBill.pos10.name}"/>
                                    <g:if test="${actualGP.qualyResult != null}">
                                        <g:set var="driverNameResult" value="${actualGP.qualyResult.pos10.name}"/>
                                    </g:if>
                                </g:if>

                                <g:else>
                                    <g:set var="driverIdSelected" value="-1"/>
                                    <g:set var="driverNameSelected" value="No selection"/>
                                </g:else>

                            </g:elseif>

                            <g:if test="${(userBilling != null)}">
                                <g:if test="${(actualGP.qualyResult != null)}">
                                    <g:if test="${driverNameSelected == driverNameResult}">
                                        <g:set var="hitsQualyCounter" value="${hitsQualyCounter + 1}"/>
                                        <span id="${i}pos_qualy" style="color: green"> <b>${driverNameSelected}</b>
                                            <span class="glyphicon glyphicon-ok"></span>
                                        </span>
                                    </g:if>
                                    <g:else>
                                        <span id="${i}pos_qualy" style="color: red"> <b>${driverNameSelected}</b>
                                            <span class="glyphicon glyphicon-remove"></span>
                                        </span>
                                    </g:else>
                                </g:if>
                                <g:else>
                                    <span id="${i}pos_qualy"> <b>${driverNameSelected}</b></span>
                                </g:else>
                            </g:if>
                            <g:else>
                                <b>No vote</b>
                            </g:else>
                        </div>
                    </g:each>
                </div>
            </div>

            <div id="raceForm" style="display:none;">


                <div class="col-lg-4 col-md-4">

                    <g:each var="i" in="${ (1..<11) }">
                        <div class="form-group">
                            <label for="${i}pos_race">Race - Position ${i} - </label>



                            <g:if test="${(i==1)}">
                                <g:set var="driverIdSelected" value="${userBilling.raceBill.pos1.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.raceBill.pos1.name}"/>
                                <g:if test="${actualGP.raceResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.raceResult.pos1.name}"/>
                                </g:if>
                            </g:if>
                            <g:elseif test="${i == 2}">
                                <g:set var="driverIdSelected" value="${userBilling.raceBill.pos2.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.raceBill.pos2.name}"/>
                                <g:if test="${actualGP.raceResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.raceResult.pos2.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 3}">
                                <g:set var="driverIdSelected" value="${userBilling.raceBill.pos3.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.raceBill.pos3.name}"/>
                                <g:if test="${actualGP.raceResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.raceResult.pos3.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 4}">
                                <g:set var="driverIdSelected" value="${userBilling.raceBill.pos4.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.raceBill.pos4.name}"/>
                                <g:if test="${actualGP.raceResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.raceResult.pos4.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 5}">
                                <g:set var="driverIdSelected" value="${userBilling.raceBill.pos5.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.raceBill.pos5.name}"/>
                                <g:if test="${actualGP.raceResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.raceResult.pos5.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 6}">
                                <g:set var="driverIdSelected" value="${userBilling.raceBill.pos6.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.raceBill.pos6.name}"/>
                                <g:if test="${actualGP.raceResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.raceResult.pos6.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 7}">
                                <g:set var="driverIdSelected" value="${userBilling.raceBill.pos7.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.raceBill.pos7.name}"/>
                                <g:if test="${actualGP.raceResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.raceResult.pos7.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 8}">
                                <g:set var="driverIdSelected" value="${userBilling.raceBill.pos8.id}"/>
                                <g:set var="driverNameSelected" value="${userBilling.raceBill.pos8.name}"/>
                                <g:if test="${actualGP.raceResult != null}">
                                    <g:set var="driverNameResult" value="${actualGP.raceResult.pos8.name}"/>
                                </g:if>
                            </g:elseif>
                            <g:elseif test="${i == 9}">

                                <g:if test="${userBilling.raceBill.pos9 != null}">
                                    <g:set var="driverIdSelected" value="${userBilling.raceBill.pos9.id}"/>
                                    <g:set var="driverNameSelected" value="${userBilling.raceBill.pos9.name}"/>
                                    <g:if test="${actualGP.raceResult != null}">
                                        <g:set var="driverNameResult" value="${actualGP.raceResult.pos9.name}"/>
                                    </g:if>
                                </g:if>

                                <g:else>
                                    <g:set var="driverIdSelected" value="-1"/>
                                    <g:set var="driverNameSelected" value="No selection"/>
                                </g:else>
                            </g:elseif>
                            <g:elseif test="${i == 10}">
                                <g:if test="${userBilling.raceBill.pos10 != null}">
                                    <g:set var="driverIdSelected" value="${userBilling.raceBill.pos10.id}"/>
                                    <g:set var="driverNameSelected" value="${userBilling.raceBill.pos10.name}"/>
                                    <g:if test="${actualGP.raceResult != null}">
                                        <g:set var="driverNameResult" value="${actualGP.raceResult.pos10.name}"/>
                                    </g:if>
                                </g:if>

                                <g:else>
                                    <g:set var="driverIdSelected" value="-1"/>
                                    <g:set var="driverNameSelected" value="No selection"/>
                                </g:else>

                            </g:elseif>

                            <g:if test="${(userBilling != null)}">
                                <g:if test="${(actualGP.raceResult != null)}">
                                    <g:if test="${driverNameSelected == driverNameResult}">
                                        <g:set var="hitsRaceCounter" value="${hitsRaceCounter + 1}"/>
                                        <span id="${i}pos_race" style="color: green"> <b>${driverNameSelected}</b>
                                        <span class="glyphicon glyphicon-ok"></span>
                                        </span>
                                    </g:if>
                                    <g:else>
                                        <span id="${i}pos_race" style="color: red"> <b>${driverNameSelected}</b>
                                        <span class="glyphicon glyphicon-remove"></span>
                                        </span>
                                    </g:else>
                                </g:if>
                                <g:else>
                                    <span id="${i}pos_race"> <b>${driverNameSelected}</b></span>
                                </g:else>
                            </g:if>
                            <g:else>
                                <b>No vote</b>
                            </g:else>
                        </div>
                    </g:each>
                </div>
            </div>




            <div id="fastLapForm" style="display:none;">


                <div class="col-lg-4 col-md-4">


                    <div class="form-group">
                        <label for="fastLap">Fast Lap - </label>


                        <g:if test="${(userBilling != null)}">
                            <g:if test="${(actualGP.fastLap != null)}">
                                <g:if test="${userBilling.fastLapBill.name == actualGP.fastLap.name}">
                                    <g:set var="hitFastLapCounter" value="${hitFastLapCounter + 1}"/>
                                    <span id="fastLap" style="color: green"> <b>${userBilling.fastLapBill.name}</b>
                                        <span class="glyphicon glyphicon-ok"></span>
                                    </span>
                                </g:if>
                                <g:else>
                                    <span id="fastLap" style="color: red"> <b>${userBilling.fastLapBill.name}</b>
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </span>
                                </g:else>
                            </g:if>
                            <g:else>
                                <span id="fastLap"> <b>${userBilling.fastLapBill.name}</b></span>
                            </g:else>

                        </g:if>
                        <g:else>
                            <b>No vote</b>
                        </g:else>

                    </select>
                    </div>

                </div>
            </div>








            <div class="col-lg-8 col-md-8">
                <div class="jumbotron">
                    <div class="summaryRace">
                        <h1>${actualGP.name}</h1>
                        <h2><g:message code="default.billings.summaryHits"/></h2>
                        <div class="summaryRaceRow">
                            <span class="glyphicon glyphicon-arrow-right"></span>
                            <g:message code="default.billings.qualy"/>:
                            <g:if test="${actualGP.qualyResult == null}">
                                <span style="color:red;">No results yet</span>
                            </g:if>
                            <g:else>
                                <span style="color:blue;">${hitsQualyCounter} hits</span>
                            </g:else>
                        </div>
                        <div class="summaryRaceRow">
                            <span class="glyphicon glyphicon-arrow-right"></span>
                            <g:message code="default.billings.race"/>:
                            <g:if test="${actualGP.raceResult == null}">
                                <span style="color:red;">No results yet</span>
                            </g:if>
                            <g:else>
                                <span style="color:blue;">${hitsRaceCounter} hits</span>
                            </g:else>
                        </div>
                        <div class="summaryRaceRow">
                            <span class="glyphicon glyphicon-arrow-right"></span>
                            <g:message code="default.billings.fastLap"/>:
                            <g:if test="${actualGP.fastLap == null}">
                                <span style="color:red;">No results yet</span>
                            </g:if>
                            <g:else>
                                <span style="color:blue;">${hitFastLapCounter} hits</span>
                            </g:else>
                        </div>


                    </div>
                </div>
            </div>
            </div>


            </div>

            </g:if>




        </div>

    </div>

</div>
</body>
</html>
