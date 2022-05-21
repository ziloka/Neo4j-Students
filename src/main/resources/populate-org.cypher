MATCH (n) DETACH DELETE n;

CREATE (Connor:Student { first_name: 'Connor', last_name: 'Wu' }),
(Tyler:Student { first_name: 'Tyler', last_name: 'Wu' }),
(Alex:Student { first_name: 'Alex', last_name: 'Xiang' })

// Create courses
CREATE (Band:Course { name: 'Band' }),
(Math: Course { name: 'Math' }),
(History: Course { name: 'History' })

// Setup relationships
// Tyler and Connor And Alex are all in band
CREATE (Connor)-[ConnorBandLabel:COURSE]->(Band),
(Tyler)-[TylerBandLabel:COURSE]->(Band),
(Alex)-[AlexBandLabel:COURSE]->(Band)
// Connor And Alex take Math together
CREATE (Connor)-[ConnorMathLabel:Course]->(Math),
(Alex)-[AlexMathLabel:Course]->(Math)
// Tyler and Alex take History together
CREATE (Tyler)-[TylerMathLabel:Course]->(History),
(Alex)-[AlexHistoryLabel:Course]->(Math);

// MATCH (n) RETURN n;