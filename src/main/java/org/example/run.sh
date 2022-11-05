echo $1
nw=$1
for n in $(seq 1 $nw) ; do
echo start worker
java --class-path /Users/amira/Downloads/RMIExample/target/classes org.example.RMIClient & done && wait