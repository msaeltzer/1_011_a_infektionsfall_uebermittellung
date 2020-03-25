<template src="./link-test-result-patient-lb.component.html"></template>
<script lang="ts">

import { Component, Vue } from 'vue-property-decorator';

@Component({
  name: 'LinkTestResultPatientLBComponent',
})

export default class LinkTestResultPatientLBComponent extends Vue {

    constructor(){
        super();
    }
    
    handleSubmit(e: any) {
      e.preventDefault();

      // TODO: Send request to connect patient with test result
      console.log("Handle connect patient with test result");

      const patientId = "198424989813";
      const testResult = "negative";

      // TODO: Handle response from server
      const response = { type: "success" };
      const { type } = response;

      // Check notification type (success, info, warning, error)
      let notification: any = {};
      if (type === "success") {
        notification.message = "Verknüpfung erfolgreich.";
        notification.description = `Patienten ID: ${patientId} & Test Resultat: ${testResult}.`;
      } else if (type === "error") {
        notification.message = "Es is ein Fehler aufgetreten.";
        notification.description = `Patienten ID: ${patientId} & Test Resultat: ${testResult}.`;
      }
      this.$notification.success(notification);
    }
}
</script>

<style lang="scss" src="./link-test-result-patient-lb.component.scss"></style>