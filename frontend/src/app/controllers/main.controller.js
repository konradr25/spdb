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

    var reinitializeMap = function() {
        scope.heatmap.setMap(null);
        scope.map = new google.maps.Map(document.getElementById('map'), {
            zoom: 13,
            center: {lat: 53.139282, lng: 23.1068179999}
        });
    }

    var updateHeatmapWithPoints = function(DelaysDTOs) {
        scope.selectedPoints = createPoints(DelaysDTOs);
        scope.heatmap = new google.maps.visualization.HeatmapLayer({
            data: scope.selectedPoints,
            map: scope.map,
            radius: 30
        });
    }

    scope.findDelays = function () {
        mainService.loadDelays(scope.chosenLine.value, scope.startDate, scope.stopDate, function (DelaysDTOs) {
            reinitializeMap();
            updateHeatmapWithPoints(DelaysDTOs);
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
            return {location: new google.maps.LatLng(delayDTO.x, delayDTO.y), weight: delayDTO.delay}
        });

    };


}

module.exports = MainController;