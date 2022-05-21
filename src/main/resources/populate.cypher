MATCH (n) DETACH DELETE n;

CREATE (Connor:Student { first_name: 'Connor', last_name: 'Wu' }),
(Tyler:Student { first_name: 'Tyler', last_name: 'Smith' }),
(Alex:Student { first_name: 'Alex', last_name: 'Xiang' })

// Create courses
CREATE (Band:Course { name: 'Band' }),
(Math: Course { name: 'Math' }),
(History: Course { name: 'History' })

// Setup relationships
// Tyler and Connor And Alex are all in band
CREATE (Connor)-[ConnorBandLabel:Taking]->(Band),
(Tyler)-[TylerBandLabel:Taking]->(Band),
(Alex)-[AlexBandLabel:Taking]->(Band)
// Connor And Alex take Math together
CREATE (Connor)-[ConnorMathLabel:Taking]->(Math),
(Alex)-[AlexMathLabel:Completed]->(Math)
// Tyler and Alex take History together
CREATE (Tyler)-[TylerMathLabel:Taking]->(History),
(Alex)-[AlexHistoryLabel:Taking]->(Math);

 --MATCH (n) RETURN n;