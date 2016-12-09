/**
 * Created by i-tang on 08/09/16.
 */
define(function (require) {
    var Backbone = require('Backbone');
    var ICsts = require('../ICsts');
    var UserModel = require('./UserModel');

    var icsts = new ICsts();
    return Backbone.Collection.extend({

        model: UserModel,
        urlRoot: icsts.SERVER_URL() + "/users",

        url: function () {
            return this.urlRoot;
        },

        print: function () {
            var result = "";
            this.forEach(function (user) {
                result += user.print();
            });
            return result;
        }
    });
});