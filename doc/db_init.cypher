CREATE (person0:Person {uid:10000,nickname:"HYisen"})
CREATE (account0:Account {username:"alexhyisen",password:"$2a$10$0HQXTZ51mNUgrcKJqKPky.EXFgEcan5rtr4Sv7OIzRAWFyBTlhYZG"})//password = 8964
CREATE (paper00:Paper {pid:4000000,title:"Hello world.",content:"Accross the Great Wall, we can reach anywhere in the world."})
CREATE (paper01:Paper {pid:4000001,title:"GNU",content:"GNU is Not Unix, a recursive description."})
CREATE (account0)-[:LINK]->(person0)
CREATE
  (person0)-[:PUBLISH{timestamp:1544031702374}]->(paper00),
  (person0)-[:PUBLISH{timestamp:1544031802374}]->(paper01)

CREATE (person1:Person {uid:10001,nickname:"Winnie the Pooh"})
CREATE (account1:Account {username:"xijinping",password:"$2a$10$pKgTteZ5iZkW406yWeWnzuvyb6rygjFcRp6bEwKC/QJ2tm/t87/BS"})//password = 666
CREATE (paper10:Paper {pid:400002,title:"Propaganda",content:"Just shut up and listen to me."})
CREATE (paper11:Paper {pid:400003,title:"DREAM",content:"May you rest in a deep and dreamless slumber."})
CREATE (paper12:Paper {pid:400004,title:"lonely fighter",content:"Even if no one understand me, I will still struggle to plug the country out."})
CREATE (account1)-[:LINK]->(person1)
CREATE
  (person1)-[:PUBLISH{timestamp:1544031602374}]->(paper10),
  (person1)-[:PUBLISH{timestamp:1544031502374}]->(paper11),
  (person1)-[:PUBLISH{timestamp:1544031402374}]->(paper12)

CREATE (person2:Person {uid:10002,nickname:"Trump"})
CREATE (account2:Account {username:"realDT",password:"$2a$10$STpMkazGGlB3YeigP8GAzufwwGPVkbEgUpnvl.dV2Z4tTYaUV7ZBK"})//password = 666
CREATE (paper20:Paper {pid:400005,title:"FAKE NEWS!",content:"MAGA!"})
CREATE (account2)-[:LINK]->(person2)
CREATE
  (person2)-[:PUBLISH{timestamp:1544031202374}]->(paper20)

CREATE (person3:Person {uid:10003,nickname:"Elder the Frog"})
CREATE (account3:Account {username:"zeminJ",password:"$2a$10$fsByQX.55P3XL10AMqoR5eF/RwHwnVQTCpEZL9xM8hRqi9f81GoFm"})//password = 123456
CREATE (paper30:Paper {pid:400006,title:"claim",content:"I'm not died until you die."})
CREATE (account3)-[:LINK]->(person3)
CREATE
  (person3)-[:PUBLISH{timestamp:1544031002374}]->(paper30)

CREATE (person4:Person {uid:10004,nickname:"Japs"})
CREATE (account4:Account {username:"abe",password:"$2a$10$2QVZyKVeBRP57tLqvVzXC.CmsLHhksl9LLz47ly2MCTNHeQcAY0aC"})//password = aaaa
CREATE (paper40:Paper {pid:400007,title:"title",content:"content"})
CREATE (account4)-[:LINK]->(person4)
CREATE
  (person4)-[:PUBLISH{timestamp:1544030802374}]->(paper40)


CREATE (person1)-[:FRIEND]->(person2)
CREATE (person1)-[:FRIEND]->(person3)
CREATE (person1)-[:FRIEND]->(person4)
CREATE (person2)-[:FRIEND]->(person4)