'use strict';

MainController.$inject = ['MainService'];
function MainController(mainService) {
    var _this = this;
    _this.lines = [];
    _this.allLines = "";
    _this.loadAllLines = function() {
        mainService.loadAllLines(function (res) {
            _this.allLines = _this.mapLines(res);
        });
    };

    _this.mapLines = function(lineNumsArray) {
        return lineNumsArray.map(function (lineNum) {
            return {
                value: lineNum,
                display: lineNum
            };
        });
    };

    _this.querySearch = function (query) {
        return this.lines.filter(function (line) { return line.value === query; });
    };

    _this.selectedItemChange = function (item) {

    }

    _this.findDelays = function () {

    }

}

module.exports = MainController;