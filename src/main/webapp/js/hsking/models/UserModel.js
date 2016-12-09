/**
 * Created by i-tang on 05/09/16.
 */
define(function (require) {
    var Backbone = require('Backbone');
    var ICsts = require('../ICsts');

    var icsts = new ICsts();

    var UserModel = Backbone.Model.extend({

        //urlRoot: icsts.SERVER_URL + "/users",

        print: function () {
            return 'id:' + this.get('id')
                + ', email:' + this.get('email')
                + ', firstName:' + this.get('firstName')
                + ', lastName: ' + this.get('lastName');
        }
    });
    return UserModel;
});