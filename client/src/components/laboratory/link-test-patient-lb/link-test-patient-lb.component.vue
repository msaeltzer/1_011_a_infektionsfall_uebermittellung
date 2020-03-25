<template src="./link-test-patient-lb.component.html"></template>
<script lang="ts">

import { Component, Prop, Vue } from 'vue-property-decorator';
import Api from "../../../api/Api";

@Component({
  name: 'LoginTestPatientLBComponent',
})

export default class LinkTestPatientLBComponent extends Vue {

    @Prop({ required: true }) 
    private laboratoryId: string;

    public form: any; 
    public createdLabTest: any;

    constructor(){
        super();
        this.laboratoryId = '';
        this.createdLabTest = null;
        this.form = this.$form.createForm(this);
    }
    
    handleSubmit(e: any) {
      e.preventDefault();

      this.form.validateFields((err: any, values: any) => {
        if (err) {
          return;
        }

        const request = {
          ...values,
          laboratoryId: this.laboratoryId
        };

        Api.postLabTest(request)
          .then(labTest => {
            this.createdLabTest = labTest;

            const notification = {
              message: "Test angelegt und verknüpft.",
              description:
                "Der Test wurde erfolgreich angelegt und mit dem Patienten verknüpft."
            };
            this.$notification["success"](notification);
          })
          .catch(err => {
            const notification = {
              message: "Fehler beim Anlegen des Tests.",
              description: err.message
            };
            this.$notification["error"](notification);
          });
      });
   }
}
</script>

<style lang="scss" src="./link-test-patient-lb.component.scss"></style>