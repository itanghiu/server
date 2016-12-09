/**
 * Created by i-tang on 04/09/16.
 */
define(function (require) {
    var Backbone = require('Backbone');
    var UserModel = require('../models/UserModel');
    var $ = require('jquery');

    var UserListButtonView = Backbone.View.extend({

        initialize: function () {
            this.render();
        },

        render: function () {
            var html = ' <input id="addUser" type="button" value="Add user">';
            $(this.el).html(html);
            return this;
        },

        events: {
            'click #addUser': 'addUser'
        },

        addUser: function () {

            var email = prompt('Enter email', 'aa@g.com');
            var firstName = prompt('Enter first Name', 'John');
            var lastName = prompt('Enter last Name', 'Travolta');
            var userModel = new UserModel({
                email: email,
                firstName: firstName,
                lastName: lastName
            });
            this.collection.add(userModel);
            userModel.save({}, {
                success: function (model, response, options) {
                    console.log('User saved to server (id:' + userModel.get('id') + ')');
                },
                error: function (model, response, options) {
                    console.log('Error saving user (status:' + response.status);
                    alert("Something went wrong while adding user (" + model.get('id') + ').');
                }
            });
        }
    });
    return UserListButtonView;
});