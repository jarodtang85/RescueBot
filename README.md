# RescueBot
## About
This project is a OOP implementation exercise, incorporating specific OOP and general programming practices such as encapsulation, polymorphism, control flow and a good understanding of data structures & algorithms. The program explores how different scenarios effects the critical decisions made by the decision algorithm. 

### Decision Algorithm/Engine Reflection
The algorithm deploys the robot at the location with the greatest number of humans. If the number of humans between several are tied, then the robot will be deployed at the location which was checked last. The reason this was implemented was to act in the best interest of the greatest number of people. 
Comparing the algorithm audit to the user audit, the algorithm’s ratio for the number of human survivors will always be greater than or equal to the user’s audit value. 

Since the algorithm only values the number of human lives at a given location, it will disregard other factors such as health, age and occupation. Additionally, the status of the characters at a given location (trespassing or legal) is also ignored. As a programmer, this unintended consequence brings the design choice made into question, as it raises ethical concerns regarding treating human lives as quantifiable rather than considering other factors listed above. 

Moreover, this utilitarian approach places the burden of harm on the location with fewer humans, raising further questions regarding the fairness and justice in the design choice. 

On a lighter note, it is observed that a higher number of pets are saved by the algorithm, potentially due to the factor that typically pets are with their owners. 

To address the aforementioned design concerns, the current algorithm could be updated such that it considers other factors besides the number of humans at one location to promote fairness and justice.
