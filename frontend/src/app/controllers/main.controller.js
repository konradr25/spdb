'use strict';

MainController.$inject = ['MainService', '$scope'];
function MainController(mainService, scope) {
    scope.heatmap = undefined;
    scope.chosenLine = undefined;
    scope.startDate = undefined;
    scope.stopDate = undefined;

    scope.$on('mapInitialized', function(event, map) {
        scope.heatmap = map.heatmapLayers.foo;
    });

    scope.allLineDTOs = [];
    scope.loadAllLines = function() {
        mainService.loadAllLines(function (res) {
            scope.allLineDTOs = mapLines(res);
        });
    };

    scope.findDelays = function () {
        mainService.loadDelays(scope.chosenLine, scope.startDate, scope.stopDate, function (res) {
            scope.selectedPoints = createPoints(res);
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
                value: lineNum,
                display: lineNum
            };
        });
    };

    var createPoints = function (allDelaysDTOs) {
        return allDelaysDTOs.map(function (delayDTO) {
            return new google.maps.LatLng(delayDTO.x, delayDTO.y);
        });
    };



    scope.selectedPoints = [ //TODO remove mock
        new google.maps.LatLng(37.782551, -122.445368),
        new google.maps.LatLng(37.782745, -122.444586),
        new google.maps.LatLng(37.782842, -122.443688),
        new google.maps.LatLng(37.782919, -122.442815),
        new google.maps.LatLng(37.782992, -122.442112),
        new google.maps.LatLng(37.783100, -122.441461),
        new google.maps.LatLng(37.783206, -122.440829),
        new google.maps.LatLng(37.783273, -122.440324),
        new google.maps.LatLng(37.783316, -122.440023),
        new google.maps.LatLng(37.783357, -122.439794),
        new google.maps.LatLng(37.783371, -122.439687),
        new google.maps.LatLng(37.783368, -122.439666),
        new google.maps.LatLng(37.783383, -122.439594),
        new google.maps.LatLng(37.783508, -122.439525),
        new google.maps.LatLng(37.783842, -122.439591),
        new google.maps.LatLng(37.784147, -122.439668),
        new google.maps.LatLng(37.784206, -122.439686),
        new google.maps.LatLng(37.784386, -122.439790),
        new google.maps.LatLng(37.784701, -122.439902),
        new google.maps.LatLng(37.784965, -122.439938),
        new google.maps.LatLng(37.785010, -122.439947),
        new google.maps.LatLng(37.785360, -122.439952),
        new google.maps.LatLng(37.785715, -122.440030),
        new google.maps.LatLng(37.786117, -122.440119),
        new google.maps.LatLng(37.786564, -122.440209),
        new google.maps.LatLng(37.786905, -122.440270),
        new google.maps.LatLng(37.786956, -122.440279),
        new google.maps.LatLng(37.800224, -122.433520),
        new google.maps.LatLng(37.800155, -122.434101),
        new google.maps.LatLng(37.800160, -122.434430),
        new google.maps.LatLng(37.800378, -122.434527),
        new google.maps.LatLng(37.800738, -122.434598),
        new google.maps.LatLng(37.800938, -122.434650),
        new google.maps.LatLng(37.801024, -122.434889),
        new google.maps.LatLng(37.800955, -122.435392),
        new google.maps.LatLng(37.800886, -122.435959)
    ];

}

module.exports = MainController;