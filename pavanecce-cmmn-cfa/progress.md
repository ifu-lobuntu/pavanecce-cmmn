#1.  Done and tested

ConversationForAction workflow: 

- request directly
- promise
- accept
- start
- get initiated commitments
- get owned commitments

#2. To implement

ConversationForAction workflow: 

- add conversation (not direct request)
- counterToInitiator
- counterToOwner
- renegotiate
- counterToRenegotiator
- counterFromRenegotiator
- renege
- withdraw
- accept new terms


Introduce custom state transitions for CMMN planning elements:

Implement ContractService

- prepare contract
- commit to contract
- consummate contract

Add a 'correlation key' parameter, the central product/offering the initiator is interested in 
- for use in VDML
- used to identify tasks
- In expressions inside a Task, make this CaseParameter available. This would help with automatic selection of other parameters based on one input parameter
- once a user has selected the correlation parameter, have a mechanism to calculate values for the other parameters after planning 

Milestone with cost breakdown (move to REA)
- introduce payment service / accounting service with multiple currencies (local, national, etc)
- issue payment automatically when milestone achieved
- a further refinement could even freeze funds on commitment
- issue data to VDML db for tasks whose completion are entry criteria ???? what did I mean here


Calendaring
- schedule tasks due date on Google?
- mark certain tasks as meetings and put them on Google Calendar

Allowing SentryInstances and OnPartInstances to be created at runtime
e-mail listener that creates tasks
allow user to associate the task with a case type and start the case 