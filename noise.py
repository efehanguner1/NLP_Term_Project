import random

## random text perturbation to create spellcheck dataset


filename = "tr.test"
filename2 = "tr_noisy.test"

out_file = open(filename2,"w",encoding="utf-8")

prob = 0.05
turkish_chars = ['a','b','c','ç','d','e','f','g','h','ı','i','j','k','l','m','n','o','ö','p','r','s','ş','t','u','ü','v','y','z']
capitals = ['A','B','C','Ç','D','E','F','G','H','I','İ','J','K','L','M','N','O','Ö','P','R','S','Ş','T','U','Ü','V','Y','Z']


output_seq = []
with open(filename, "r", encoding="utf-8") as f:
    
    for line in f:
        output_seq = ""
        for chr in line:
                if chr in turkish_chars:
                    if random.random() < prob:
                        chr = random.choice(turkish_chars)                    
                output_seq += chr              
                    
        #print(output_seq)
        out_file.write(output_seq)
        #out_file.write("\n")

out_file.close()

