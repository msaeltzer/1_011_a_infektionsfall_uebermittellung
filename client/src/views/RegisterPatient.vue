<template>
  <div class="wrapper">

    <!-- Patient successfully created info -->
    <div v-if="createdPatient" style="display: flex; justify-content: center; text-align: center">
      <a-card style="max-width: 600px; margin-bottom: 25px">
        <div style="display: flex; flex-direction: column; position: relative">
          <a-icon type="close" style="top: 0; right: 0; position: absolute; cursor: pointer"
                  @click="createdPatient = null" />
          <div style="display: flex; align-items: center; margin: 10px">
            <a-icon type="check-circle" :style="{ fontSize: '38px', color: '#08c' }" style="margin: 0 25px 0 0" />
            <h3 style="margin-bottom: 0">
              <span v-if="createdPatient.gender === 'female'">Patientin</span>
              <span v-else>Patient</span>
              wurde erfolgreich registriert.
            </h3>
          </div>
          <table style="border-collapse: separate; border-spacing: 15px 5px; text-align: left">
            <tbody>
              <tr>
                <td>Name:</td>
                <td>{{ createdPatient.firstName }} {{ createdPatient.lastName }}</td>
              </tr>
              <tr>
                <td>Patienten-ID:</td>
                <td>{{ createdPatient.id }}</td>
              </tr>
            </tbody>
          </table>
          <div style="margin-top: 15px">
            <router-link :to="{ name: 'patient-detail', params: { id: createdPatient.id } }" style="margin-right: 15px">
              <a-button type="primary">
                Patienten einsehen
              </a-button>
            </router-link>
            <router-link :to="{ name: 'register-test' }">
              <a-button type="primary">
                Probe zuordnen
              </a-button>
            </router-link>
          </div>
        </div>
      </a-card>
    </div>

    <!-- Patient Registration Form -->
    <div>
      <h3>
        Registrieren Sie hier neue Patienten in IMIS. Bitte füllen Sie den Bogen
        vollständig aus.
      </h3>

      <a-form
        :form="form"
        :layout="'horizontal'"
        :labelCol="{ span: 8 }"
        :wrapperCol="{ span: 16 }"
        @submit.prevent="handleSubmit"
      >

        <a-collapse defaultActiveKey="1">

          <!-- Stammdaten -->
          <a-collapse-panel header="Allgemeines" key="1">
            <PatientStammdaten :form="form" @gender="genderSelected" :show-stay="true" :show-death="true" />
          </a-collapse-panel>

          <!-- Infektionskette / Exposure -->
          <a-collapse-panel header="Exposition" key="2">
            <a-form-item
              :label="'Bitte zutreffendes ankreuzen:'"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-checkbox-group v-decorator="['exposures']" @change="exposuresChanged">
                <a-row>
                  <a-col :span="24" v-for="exposure in EXPOSURES_INTERNAL" :key="exposure.value">
                    <a-checkbox :value="exposure.value">
                      {{exposure.label}}
                    </a-checkbox>
                  </a-col>
                </a-row>
              </a-checkbox-group>
              <a-checkbox-group v-decorator="['exposureLocation']" :disabled="disableExposureLocation"
                                style="padding-left: 30px">
                <a-row>
                  <a-col :span="24" v-for="exposure in EXPOSURE_LOCATIONS" :key="exposure.value">
                    <a-checkbox :value="exposure.value">
                      {{exposure.label}}
                    </a-checkbox>
                  </a-col>
                </a-row>
              </a-checkbox-group>
            </a-form-item>
          </a-collapse-panel>

          <!-- Symptoms -->
          <a-collapse-panel header="Symptome" key="3">
            <a-form-item
              :label="'Welche Symptome weist ' + patientString + ' auf?'"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-checkbox-group v-decorator="['symptoms']">
                <a-row>
                  <a-col :span="symptom.value === 'LOSS_OF_SENSE_OF_SMELL_TASTE' ? 12 : 6" v-for="symptom in SYMPTOMS"
                         :key="symptom.value">
                    <a-checkbox :value="symptom.value">
                      {{symptom.label}}
                    </a-checkbox>
                  </a-col>
                  <a-col :span="12" v-for="symptom in ADDITIONAL_SYMPTOMS" :key="symptom.value">
                    <a-checkbox :value="symptom.value">
                      {{symptom.label}}
                    </a-checkbox>
                  </a-col>
                </a-row>
              </a-checkbox-group>
              <div style="display: flex; align-items: center; align-self: stretch">
                <a-checkbox :checked="showOtherSymptoms" @change="symptomsChanged" style="flex: 0 0 auto">
                  Andere:
                </a-checkbox>
                <a-form-item style="flex: 1 1 100%; margin-bottom: 0; max-width: 600px">
                  <a-input v-decorator="['symptomsOther']" :disabled="!showOtherSymptoms" />
                </a-form-item>
              </div>
            </a-form-item>

            <a-form-item
              label="Wie schnell sind die Beschwerden aufgetreten?"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-radio-group
                v-decorator="['speedOfSymptomsOutbreak']"
              >
                <a-radio value="suddenly">
                  Plötzlich, innerhalb von einem Tag
                </a-radio>
                <a-radio value="slow">
                  Langsam, innerhalb von mehreren Tagen
                </a-radio>
              </a-radio-group>
            </a-form-item>

            <a-form-item
              :label="'Hat ' + patientString + ' für diese Saison eine Influenza-Impfung erhalten?'"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-radio-group v-decorator="['fluImmunization']">
                <a-radio value="true">Ja</a-radio>
                <a-radio value="false">Nein</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-collapse-panel>

          <!-- Risks / Pre Illnesses -->
          <a-collapse-panel header="Vorerkrankungen" key="4">
            <a-form-item
              :label="'Welche Vererkrankungen und Risikofaktoren liegen vor?'"
              :labelCol="{ div: 24 }"
              :wrapperCol="{ div: 24 }"
            >
              <a-checkbox-group v-decorator="['preIllnesses']">
                <a-row>
                  <a-col :span="8" v-for="preIllness in PRE_ILLNESSES" :key="preIllness.value">
                    <a-checkbox :value="preIllness.value">
                      {{preIllness.label}}
                    </a-checkbox>
                  </a-col>
                </a-row>
              </a-checkbox-group>
            </a-form-item>
          </a-collapse-panel>

          <!-- Krankheitsdetails -->
          <a-collapse-panel header="Krankheit" key="5">
            <a-row>
              <a-col :span="4" />
              <a-col :span="20">
                <div style="display: flex; align-items: center;">
                  <a-form-item :wrapperCol="{span: 24}">
                    <a-checkbox :checked="!disableHospitalization" @change="hospitalizationChanged">
                      {{patientString}} ist hospitalisiert
                    </a-checkbox>
                  </a-form-item>
                  <DateInput style="flex: 0 1 400px" label="Seit" :decorator="['dateOfHospitalization']"
                             :disabled="disableHospitalization" />
                  <a-form-item style="padding-left: 15px;" :wrapperCol="{span: 24}">
                    <a-checkbox :disabled="disableHospitalization" :decorator="['onIntensiveCareUnit']">
                      Auf der Intensivstation
                    </a-checkbox>
                  </a-form-item>
                </div>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="Art der Erkrankung">
                  <a-select
                    v-decorator="['illnessType', { rules: [{
                      required: true,
                      message: 'Bitte Erkrankung wählen',
                    }], initialValue: 'CORONA'}]"
                    placeholder="Bitte wählen..."
                  >
                    <a-select-option value="CORONA">SARS-nCoV-2</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <DateInput label="Erkrankungsdatum" :decorator="['dateOfIllness', { rules: [{
                      required: true,
                      message: 'Bitte Erkrankungsdatum wählen',
                    }], initialValue: today}]" />
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="Fallstatus">
                  <a-select style="width: 250px" placeholder="Status" v-decorator="['patientStatus', { rules: [{
                      required: true,
                      message: 'Bitte Status wählen',
                    }], initialValue: 'SUSPECTED' }]">
                    <a-select-option v-for="eventType in EVENT_TYPES" :key="eventType.id">
                      <a-icon :type="eventType.icon" style="margin-right: 5px" />
                      {{eventType.label}}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <DateInput label="Meldedatum" :decorator="['dateOfReporting', { rules: [{
                      required: true,
                      message: 'Bitte Meldedatum wählen'
                    }], initialValue: today}]" />
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="4" />
              <a-col :span="20">
                <a-checkbox @change="testOrderedChanged" style="margin-bottom: 15px"
                            :checked="!disableTestOrder">
                  Wurde eine Erregerdiagnostik beauftragt?
                </a-checkbox>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <LaboratoryInput
                  label="Labor"
                  :form="form"
                  :validation="['laboratoryId']"
                  :disabled="disableTestOrder"
                />
              </a-col>
              <a-col :span="12">
                <DateInput label="Datum der Probenentnahme" :decorator="['dateOfTest']" :disabled="disableTestOrder" />
              </a-col>
            </a-row>
          </a-collapse-panel>
        </a-collapse>

        <!-- Submit Button -->
        <a-form-item :wrapperCol="{ span: 24, offset: 0 }" style="margin-top: 15px">
          <a-row :gutter="16" type="flex" justify="end">
            <a-col>
              <a-button type="primary" html-type="submit" size="large">
                <a-icon type="save" />
                Patient registrieren
              </a-button>
            </a-col>
          </a-row>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import Api from '@/api'
import { Patient } from '@/api/SwaggerApi'
import PatientStammdaten from '@/components/PatientStammdaten.vue'
import { ADDITIONAL_SYMPTOMS, SYMPTOMS } from '@/models/symptoms'
import { Option } from '@/models'
import { PRE_ILLNESSES } from '@/models/pre-illnesses'
import { EXPOSURE_LOCATIONS, EXPOSURES_INTERNAL } from '@/models/exposures'
import DateInput from '@/components/DateInput.vue'
import { EventTypeItem, eventTypes } from '@/models/event-types'
import moment, { Moment } from 'moment'
import LaboratoryInput from '@/components/LaboratoryInput.vue'

interface State {
  form: any;
  createdPatient: Patient | null;
  SYMPTOMS: Option[];
  PRE_ILLNESSES: Option[];
  ADDITIONAL_SYMPTOMS: Option[];
  EXPOSURES_INTERNAL: Option[];
  EXPOSURE_LOCATIONS: Option[];
  EVENT_TYPES: EventTypeItem[];
  showOtherSymptoms: boolean;
  patientString: string;
  disableExposureLocation: boolean;
  disableHospitalization: boolean;
  disableTestOrder: boolean;
  today: Moment;
}

export default Vue.extend({
  components: {
    PatientStammdaten,
    DateInput,
    LaboratoryInput,
  },
  name: 'RegisterPatient',
  data(): State {
    return {
      form: this.$form.createForm(this, { name: 'coordinated' }),
      createdPatient: null,
      SYMPTOMS,
      ADDITIONAL_SYMPTOMS,
      PRE_ILLNESSES,
      EXPOSURES_INTERNAL,
      EXPOSURE_LOCATIONS,
      EVENT_TYPES: eventTypes,
      disableExposureLocation: true,
      showOtherSymptoms: false,
      patientString: 'der Patient',
      disableHospitalization: true,
      disableTestOrder: true,
      today: moment(),
    }
  },
  methods: {
    handleSubmit() {
      this.form.validateFields(async(err: Error, values: any) => {
        if (err) {
          return
        }
        const request = {
          ...values,
          dateOfBirth: values.dateOfBirth.format('YYYY-MM-DD'),
          riskAreas: [],
        }

        if (values.dateofIllness) {
          request.dateOfIllness = values.dateOfIllness.format('YYYY-MM-DD')
        } else {
          request.dateOfIllness = moment().format('YYYY-MM-DD')
        }

        if (values.dateOfReporting) {
          request.dateOfReporting = values.dateOfReporting.format('YYYY-MM-DD')
        } else {
          request.dateOfReporting = moment().format('YYYY-MM-DD')
        }

        if (!request.symptoms) {
          request.symptoms = []
        }
        if (!request.preIllnesses) {
          request.preIllnesses = []
        }

        if (request.dateOfDeath) {
          request.dateOfDeath = request.dateOfDeath.format('YYYY-MM-DD')
        }

        if (!this.disableHospitalization) {
          request.dateOfHospitalization = values.dateOfHospitalization.format('YYYY-MM-DD')
          request.onIntensiveCareUnit = values.onIntensiveCareUnit
        } else {
          request.dateOfHospitalization = null
          request.onIntensiveCareUnit = null
        }

        if (!this.disableTestOrder) {
          request.laboratoryId = values.laboratoryId
          request.dateOfTest = values.dateOfTest.format('YYYY-MM-DD')
        } else {
          request.laboratoryId = null
          request.dateOfTest = null
        }

        if (values.exposures) {
          request.riskAreas = request.riskAreas.concat(values.exposures)
        }
        if (this.showOtherSymptoms) {
          request.symptoms.push(values.symptomsOther)
        }
        if (values.exposureLocation) {
          request.riskAreas = request.riskAreas.concat(
            values.exposureLocation
              .map((location: string) => 'CONTACT_WITH_CORONA_' + location),
          )
        }

        const patientString = request.gender === 'female' ? 'Patientin' : 'Patient'

        Api.api.addPatientUsingPost(request).then((patient: Patient) => {
          this.form.resetFields()
          this.createdPatient = patient as any
          this.disableExposureLocation = true
          this.disableTestOrder = true
          this.disableHospitalization = true
          this.showOtherSymptoms = false
          this.form.setFieldsValue({
            symptomsOther: undefined,
            symptomsOtherActivated: undefined,
          })
          const notification = {
            message: patientString + ' registriert.',
            description: 'Der ' + patientString + ' wurde erfolgreich registriert.',
          }
          this.$notification.success(notification)
          window.scrollTo(0, 0)
        }).catch((error: Error) => {
          console.error(error)
          const notification = {
            message: patientString + ' nicht registriert.',
            description: 'Der ' + patientString + ' konnte nicht registriert werden.',
          }
          this.$notification.error(notification)
        })
      })
    },
    symptomsChanged(event: Event) {
      const target = event.target as any
      this.showOtherSymptoms = target.checked
    },
    genderSelected(gender: string) {
      console.log(gender)
      this.patientString = gender === 'female' ? 'die Patientin' : 'der Patient'
    },
    exposuresChanged(checkedValues: string[]) {
      this.disableExposureLocation = !checkedValues.includes('CONTACT_WITH_CORONA_CASE')
    },
    hospitalizationChanged(event: Event) {
      const target = event.target as any
      this.disableHospitalization = !target.checked
    },
    testOrderedChanged(event: Event) {
      const target = event.target as any
      this.disableTestOrder = !target.checked
    },
  },
})
</script>

<style scoped>
  .wrapper {
    text-align: left;
    padding: 2%;
    width: 100%;
  }

</style>
