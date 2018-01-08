'use strict';

MainService.$inject = ['$http'];
function MainService($http) {
    var _this = this;
    _this.loadAllLines = function loadAllLines(success, error) {
        $http.get('dictionary/lineNames').success(function (res) {
            success(res);
        }).error(error);
    };

    _this.loadDelays = function (lineName, start, stop, success, error) {
        $http.get('delays/lineName/' + lineName + '/start/' + start + '/stop/' + stop).success(function (res) {
            success(res);
        }).error(error);
    };

}


module.exports = MainService;