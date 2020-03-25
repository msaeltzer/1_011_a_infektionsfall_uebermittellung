import LinkTestResultAndPatientPage from "../components/page/link-test-result-patient/link-test-result-patient.component.vue";
import LinkTestAndPatientPage from "../components/page/link-test-patient/link-test-patient.component.vue";
import RegisterInstitutionPage from "../components/page/register-institution/register-institution.component.vue";
import RegisterPatientPage from "../components/page/register-patient/register-patient.component.vue";

import ViewAllDataComponent from "../components/view-all-data/view-all-data.component.vue";
import PublicStatisticsComponent from "../components/public-statistics/public-statistics.component.vue";
import PatientOverviewComponent from "../components/patient-overview/patient-overview.component.vue";



export const routes = [
/*   {
    path: '/',
    name: 'Home',
    component: Home,
  }, */
  { path: "*", redirect: "/register-patient" },
  {
    path: "/link-test-result",
    component: LinkTestResultAndPatientPage
  },
  {
    path: "/register-institution",
    component: RegisterInstitutionPage
  },
  {
    path: "/public-statistics",
    component: PublicStatisticsComponent
  },
  {
    path: "/register-patient",
    component: RegisterPatientPage
  },
  {
    path: "/patient-overview",
    component: PatientOverviewComponent
  },
  {
    path: "/all-data",
    component: ViewAllDataComponent
  },
  {
    path: "/link-test-and-patient",
    component: LinkTestAndPatientPage
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
  },
];


