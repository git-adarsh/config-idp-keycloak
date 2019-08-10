 Configurable IDP in Keycloak
 </br>
 This project enables using multiple IDPs by providing appropriate value to the query parameter <code>kc_idp_hint</code>.
 
 Following are the steps to setup and run this project:
 <ol>
  <li>
  Register your app with one or more IDP. 
  </li>
  <li>
  Enter the "client_secret" and "client_id" recieved while registering on Keycloak. You can see Keycloak documents for more.
  </li>
  <li>
  You are almost set. Just make sure whichever IDP you want to use, is registered at Keycloak. Now, put the alias of the IDP
  in the query parameter <code>kc_idp_hint</code>.
  </li>
  <li>
  Just pull or clone the project and maven-run the project.
  </li>
  
</ol>
