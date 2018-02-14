var deminer = angular.module("deminer", []);

deminer.controller("gameController", function($scope, $http, $log){
    
    $scope.handleClick = function(event){
        var cell = getClickedCell(event);
        if (isLeftClick(event)){
            check(cell);
        }
        else if (isRightClick(event)){
            setFlagFor(cell);
        }
    };   

    function isLeftClick(event){
        return event.which === 1;
    }

    function isRightClick(event){
        return event.which === 3;
    }
    
    function getClickedCell(event){
        var clickTarget = event.target;
        if ($(clickTarget).hasClass("cell")){
            return clickTarget;
        }
        else {
            return $(clickTarget).parents(".cell");
        }
    }

    function check(cell){ 
        var uri = createUriFor(cell);
        var data = {action : "CHECK"};
        $http.put(uri, data).success(updateGame).error(errorCallback);
    }

    function setFlagFor(cell){
        var uri = createUriFor(cell);
        var data = {action : "SETFLAG"};
        $http.put(uri, data).success(updateGame).error(errorCallback);
    }
    
    function createUriFor(cell){
        var x = $(cell).data("x");
        var y = $(cell).data("y");
        return "/game/cell/" + x + "/" + y;
    }
    
    function updateGame(){
        $http.get("/game/state").success(updateGameSuccessCallback).error(errorCallback);
    }
    
    function updateGameSuccessCallback(gameState){
        checkGameState(gameState);
        updateNumberOfRemainingFlags(gameState);
        updateAnalysedCells(gameState);
    }
    
    function checkGameState(gameState){
        if (gameState.minefieldState === "DEMINED"){
            showWinMessage();
        }
        else if (gameState.minefieldState === "EXPLODED"){
            showFailMessage();
        }
    }
    
    function updateNumberOfRemainingFlags(gameState){
        var totalNumberOfFlags = $("#total-flags-count").html();
        var numberOfFlaggedCells = getNumberOfFlaggedCells(gameState);
        var numberOfRemainingFlags = totalNumberOfFlags - numberOfFlaggedCells;
        $("#remaining-flags-count").html(numberOfRemainingFlags);        
    }
    
    function getNumberOfFlaggedCells(gameState){
        var numberOfFlaggedCells = 0;
        $.each(gameState.analysedCells, function(i, cell){
            if (cell.state === "FLAGGED"){
                numberOfFlaggedCells++;
            }
        });
        return numberOfFlaggedCells;
    }
    
    function showWinMessage(){
        hideTimerAndRemainingFlagsCount();
        $("#elapsed-time").html(elapsedTime);
        $("#win-message").show(100);
    }
    
    function showFailMessage(){
        hideTimerAndRemainingFlagsCount();
        $("#fail-message").show(100);
    }
    
    function hideTimerAndRemainingFlagsCount(){
        $("#timer, #remaining-flags").hide();
    }
    
    function updateAnalysedCells(gameState){
        setUncheckedStateForAllCells();
        $.each(gameState.analysedCells, function(i, cell){
            removeUncheckedStateFor(cell);
            addCurrentStateFor(cell);
        });
    }
    
    function setUncheckedStateForAllCells(){
        $(".cell").removeClass("analysed checked flagged exploded")
            .addClass("unchecked").html("?");
    }
    
    function addCurrentStateFor(cell){
        switch (cell.state){
            case "CHECKED":
                setCheckedStateFor(cell);
                break;
            case "FLAGGED":                
                setFlaggedStateFor(cell);                
                break;
            case "EXPLODED":                
                setExplodedStateFor(cell);                
                break;
            default:
                $log.error("[" + cell.state + "] cell state is incorrect");
        }
    }
    
    function setCheckedStateFor(cell){        
        var minesAround = cell.minesAround === 0 ? "" : cell.minesAround;
        $(getCellSelector(cell)).addClass("analysed checked").html(minesAround);
    }
    
    function setFlaggedStateFor(cell){
        $(getCellSelector(cell)).addClass("analysed flagged")
            .html("<span class=\"glyphicon glyphicon-flag\"></span>");
    }
    
    function setExplodedStateFor(cell){
        $(getCellSelector(cell)).addClass("analysed exploded")
            .html("<span class=\"glyphicon glyphicon-fire\"></span>");
    }
    
    function removeUncheckedStateFor(cell){
        $(getCellSelector(cell)).removeClass("unchecked");
    }
    
    function getCellSelector(cell){        
        return ".cell-" + cell.x + "-" + cell.y;
    }
    
    function errorCallback(data, status){
        $log.error(status + " ---> " + data);
    }    
    
    $scope.customize = function(){
        $("#customize-form").toggle(100);
    };   
    
    $scope.verifyCustomizationData = function(){
        var width = $("input[name='width']").val();
        var height = $("input[name='height']").val();
        var numberOfMines = $("input[name='numberOfMines']").val();
        var isCorrect = true;
        var errorMessage = "";
        if (width.length === 0 || height.length === 0 || 
            numberOfMines.length === 0)
        {
            isCorrect = false;
            errorMessage = "Not all fields were filed.";
        }
        else if (width < 1 || height < 1 || numberOfMines < 1){
            isCorrect = false;
            errorMessage = "Width, height or number of mines can't be less than or equal to zero.";
        }
        else if (width > 18){
            isCorrect = false;
            errorMessage = "Width can't be more than 18. You can increase height.";
        }
        if (isCorrect){
            $("#customize-submit-btn").click();
        }
        else {
            $("#customization-form-error").html(errorMessage).show(100);
        }
    };
});

$("#customize-form input").mask("0#");

// Timer

var startTime = new Date();
var elapsedTime = "00:00:00";

function updateTimer(){
    var second = 1000;
    var minute = second * 60;
    var hour = minute * 60;
    
    var now = new Date();
    var elapsedTimeInMilliseconds = now - startTime;
    
    var hours = Math.floor(elapsedTimeInMilliseconds / hour);
    var minutes = Math.floor((elapsedTimeInMilliseconds % hour) / minute);
    var seconds = Math.floor((elapsedTimeInMilliseconds % minute) / second);
    
    var hh = hours < 10 ? "0" + hours : hours;
    var mm = minutes < 10 ? "0" + minutes : minutes;
    var ss = seconds < 10 ? "0" + seconds : seconds;
    
    $("#hours").html(hh);
    $("#minutes").html(mm);
    $("#seconds").html(ss);
    
    elapsedTime = hh + ":" + mm + ":" + ss;
}

setInterval(function(){
    updateTimer();
}, 1000);
