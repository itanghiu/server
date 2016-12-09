/**
 * Created by i-tang on 04/09/16.
 */
define(function (require) {

    var Backbone = require('Backbone');
    var UserModel = require('../models/UserModel');
    var $ = require('jquery');

    var UserView = Backbone.View.extend({

        tagName: 'tr',// Define element tag name.

        initialize: function () {
            this.listenTo(this.model, "change", this.render);
            this.listenTo(this.model, 'destroy', this.destroy, this);
        },

        destroy: function () {
            this.remove();
        },

        render: function () {
            // Add cells to the table row.append
            $(this.el).html(_.map([
                this.model.get('email'),
                this.model.get('firstName'),
                this.model.get('lastName'),
                '<input type="submit" id="editUser" value="edit"/>',
                '<input type="submit" id="deleteUser" value="delete"/>'
            ], function (val, key) {
                return '<td>' + val + '</td>'
            }));
            return this;
        },

        events: {
            'click #editUser': "editUser",
            'click #deleteUser': "deleteUser"
        },

        editUser: function () {

            var firstName = prompt('Enter first Name', 'John');
            var lastName = prompt('Enter last Name', 'Travolta');
            this.model.set('firstName', firstName);
            this.model.set('lastName', lastName);
            var userToUpdate = this.model;
            var self = this;
            //userToUpdate.id = userToUpdate.get("id");
            var userId = userToUpdate.get("id");
            userToUpdate.save({}, {
                success: function (model, response, options) {
                    console.log("The model has been updated to the server");
                    self.collection.push(model, { merge: true });
                },
                error: function (model, xhr, options) {
                    console.log("Something went wrong while updating user (" + model.get('id') + ').');
                    alert("Something went wrong while updating user (" + model.get('id') + ').');
                }
            });
        },

        deleteUser: function () {

            var userToUpdate = this.model;
            var self = this;
            //userToUpdate.id = userToUpdate.get("id");
            userToUpdate.destroy({
                success: function (model, response, options) {
                    console.log("The user has been deleted from the server");
                    self.collection.remove(model);
                },
                error: function (model, xhr, options) {
                    console.log("Something went wrong while deleting  user (" + model.get('id') + ').');
                    alert("Something went wrong while deleting user (" + model.get('id') + ').');
                }
            });
        }
    });
    return UserView;
})
