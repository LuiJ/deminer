<!DOCTYPE html>
<html ng-app="deminer">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0" />
        <title>Deminer</title>
        <link rel="stylesheet" href="/static/styles/bootstrap.min.css" type="text/css" />
        <link rel="stylesheet" href="/static/styles/deminer.css" type="text/css" /> 
    </head>

    <body ng-controller="gameController">
        
        <div id="gameWrapper">
            
            <div id="header">
                <a href="/" id="logo" class="glyphicon glyphicon-fire"></a>
                <p id="game-name">deminer</p>
                <a href="/game/default" id="restore-default-btn" class="btn btn-default">Restore default</a>
                <button id="customize-btn" class="btn btn-default" ng-click="customize()">Customize</button>
                <div class="clear"></div>
            </div>
            
            <#if errorMessage??>
            <div class="alert alert-danger">${errorMessage}</div>
            </#if>
            
            <div id="customize-form" class="panel panel-success">
                <div class="panel-heading">Customize minefield</div>
                <div class="panel-body">
                    <div id="customization-form-error" class="alert alert-danger"></div>
                    <form method="POST" action="/game/custom" class="form-inline" role="form">
                        <div class="form-group">
                            <input type="text" class="form-control" name="width" placeholder="Width">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="height" placeholder="Height">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="numberOfMines" placeholder="Number of mines">
                        </div>
                        <button type="button" class="btn btn-success" ng-click="verifyCustomizationData()">Customize</button>
                        <input type="submit" id="customize-submit-btn" />
                    </form>
                </div>
            </div>
            
            <p id="timer">
                <span id="timer-ico" class="glyphicon glyphicon-time"></span>
                <span id="minutes">00</span>:<span id="seconds">00</span>
            </p>
            
            <p id="remaining-flags">
                <span id="remaining-flags-ico" class="glyphicon glyphicon-flag"></span>
                <span id="remaining-flags-count">${game.configuration.numberOfMines}</span>
                <span id="total-flags-count">${game.configuration.numberOfMines}</span>
            </p>
            
            <div id="fail-message" class="game-result-message alert alert-danger">
                Oops! You have lost the game.&nbsp;
                <a href="/game" class="btn btn-danger">Try again</a>
                &nbsp;or&nbsp;
                <p class="btn btn-danger" ng-click="customize()">Customize game</p>
            </div>
            <div id="win-message" class="game-result-message alert alert-success">
                You are the WINNER !&nbsp;
                <a href="/game" class="btn btn-success">Try again</a>
                &nbsp;or&nbsp;
                <p class="btn btn-success" ng-click="customize()">Customize game</p>
            </div>
            
            <table id="minefield">
            <#assign width=game.configuration.width height=game.configuration.height>
            <#list height .. 1 as y>
                <tr>
                <#list 1 .. width as x>
                    <td class="cell cell-${x}-${y} unchecked" ng-mousedown="handleClick($event)" oncontextmenu="return false" data-x="${x}" data-y="${y}">?</td>
                </#list>
                </tr>
            </#list>
            </table>
            
        </div>
        
        <script type="text/javascript" src="/static/js/jquery-1.11.0.js"></script> 
        <script type="text/javascript" src="/static/js/jquery.mask.js"></script>       
        <script type="text/javascript" src="/static/js/bootstrap.min.js"></script> 
        <script type="text/javascript" src="/static/js/angular-1.4.7.js"></script>
        <script type="text/javascript" src="/static/js/deminer.js"></script>
    </body>
</html>