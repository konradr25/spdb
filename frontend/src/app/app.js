import angular from 'angular';

import '../style/app.css';

let app = () => {
  return {
    template: require('./app.html'),
    controller: 'AppCtrl',
    controllerAs: 'app'
  }
};

class AppCtrl {
    constructor() {
        this.url = 'https://github.com/preboot/angular-webpack';
        this.loadAllLines();

    }

    mapLines(lineNumsArray) {
        return lineNumsArray.map(function (lineNum) {
            return {
                value: lineNum,
                display: lineNum
            };
        });
    }

    loadAllLines() {
        $http.get('dictionary/lineNames').success(function(data) {
            console.log("success!");
            $scope.lines = this.mapLines(data.lineNumsArray);
            console.log(data.lineNumsArray);
        });
    };

    querySearch(query) {
        return this.lines.filter(function (line) { return line.value === query; });
    };

    selectedItemChange (item) {

    }



}

const MODULE_NAME = 'app';

angular.module(MODULE_NAME, ['ngMaterial', 'ngMap'])
  .directive('app', app)
  .controller('AppCtrl', AppCtrl);

export default MODULE_NAME;
