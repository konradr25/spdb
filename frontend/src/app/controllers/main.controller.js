'use strict';

MainController.$inject = ['MainService', '$scope'];
function MainController(mainService, scope) {
    scope.heatmap = undefined;
    scope.chosenLine = undefined;
    scope.startDate = undefined;
    scope.stopDate = undefined;
    scope.selectedPoints = [];
    scope.map = undefined;

    scope.$on('mapInitialized', function(event, map) {
        scope.map = map;
        scope.heatmap = map.heatmapLayers.foo;
        scope.loadAllLines();
    });

    scope.allLineDTOs = [];
    scope.loadAllLines = function() {
        mainService.loadAllLines(function (res) {
            scope.allLineDTOs = mapLines(res);
        }, function () {
            console.log("cannot load lines");
        });
    };

    scope.findDelays = function () {
        mainService.loadDelays(scope.chosenLine.value, scope.startDate, scope.stopDate, function (res) {
            scope.selectedPoints = createPoints(res);
            scope.heatmap = new google.maps.visualization.HeatmapLayer({
                data: scope.selectedPoints,
                map: scope.map
            });
        })
    };

    scope.querySearch = function (query) {
        return this.allLineDTOs.filter(function (line) { return line.value === query; });
    };

    scope.selectedItemChange = function (item) {
        //TODO
    }

    var mapLines = function(lineNumsArray) {
        return lineNumsArray.map(function (lineNum) {
            return {
                value: lineNum.name,
                display: lineNum.name
            };
        });
    };

    var createPoints = function (allDelaysDTOs) {
        return allDelaysDTOs.map(function (delayDTO) {
            return new google.maps.LatLng(delayDTO.x, delayDTO.y);
        });

    };


}

module.exports = MainController;