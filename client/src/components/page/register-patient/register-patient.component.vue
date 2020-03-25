<template src="./register-patient.component.html"></template>
<script lang="ts">

import { Component, Vue } from 'vue-property-decorator';
import { randomizeProperties } from "../../../util/randomize";
import Api from "../../../api/Api";
import { RISK_AREAS, SYMPTOMS, ILLNESSES } from './data';

interface ConditionMap {
    [key: string]: boolean
}

@Component({
  name: 'RegisterPatientComponent',
})

export default class RegisterPatientComponent extends Vue {

  // selected items
  public selectedSymptoms: ConditionMap;
  public selectedPreIllnesses: ConditionMap;
  public selectedRiskAreas: ConditionMap;

  // possible items
  public SYMPTOMS: any;
  public ILLNESSES: any;
  public RISK_AREAS: any;

  // meta
  public createdPatient: any;
  public dataProcessingClass: any;

  // form
  public form: any;
  public checked: boolean;
  public dateFormat: string;

  constructor(){
      super();
      this.selectedSymptoms = this.initConditions(SYMPTOMS);
      this.selectedPreIllnesses = this.initConditions(ILLNESSES);
      this.selectedRiskAreas = this.initConditions(RISK_AREAS);
      
      this.SYMPTOMS = SYMPTOMS;
      this.ILLNESSES = ILLNESSES;
      this.RISK_AREAS =RISK_AREAS;

      this.createdPatient = null;
      this.dataProcessingClass = "";

      this.checked = false;
      this.dateFormat = "DD/MM/YYYY";
      this.form = this.$form.createForm(this)
  }

  onCheck(e: any) {
    this.checked = e.target.checked;
  }

  handleSubmit(e: any) {
    e.preventDefault();

    this.form.validateFields((err: any, values: any) => {
      
      // check form
      if (!this.checked) {
        this.dataProcessingClass = "data-processing-not-selected";
        return;
      } else if (err) {
        return;
      }

      // TODO: Remove this when we go to production
      randomizeProperties(
        [
          "lastName",
          "firstName",
          "email",
          "phoneNumber",
          "street",
          "houseNumber",
          "city",
          { key: "zip", type: "number" },
          "insuranceCompany",
          "insuranceMembershipNumber"
        ], values);

      // get selected symptoms
      const symptoms = SYMPTOMS.filter(
        symptom => this.selectedSymptoms[symptom.key]
      ).map(symptom => symptom.key);

      // get selected preillnesses
      const preIllnesses = ILLNESSES.filter(
        illness => this.selectedPreIllnesses[illness.key]
      ).map(illness => illness.key);

      // get selected riskareas
      const riskAreas = RISK_AREAS.filter(
        riskArea => this.selectedRiskAreas[riskArea.key]
      ).map(riskArea => riskArea.key);
    
      // create request object
      const request = {
        ...values,
        dateOfBirth: values["dateOfBirth"].format("YYYY-MM-DD") + " 00",
        symptoms,
        preIllnesses,
        riskAreas
      };

    // post request 
    Api.postPatient(request)
      .then(response => {
        this.createdPatient = response;

        // create success notfication
        const notification = {
          message: "Patient registriert.",
          description: "Der Patient wurde erfolgreich registriert."
        };
        this.$notification["success"](notification);
      })
      .catch(err => {

        // create error notification
        const notification = {
          message: "Fehler beim Registrieren des Patienten.",
          description: err.message
        };
        this.$notification["error"](notification);
      });

    });
  }

  private initConditions( condition: Array<{key: string, description: string}>): ConditionMap{
    let conditionMap: ConditionMap = {};
    condition.forEach( condition => {
      conditionMap[condition.key] = false;
    })

    return conditionMap;
  }
}
</script>

<style lang="scss" src="./register-patient.component.scss"></style>
