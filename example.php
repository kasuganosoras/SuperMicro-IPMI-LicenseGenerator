<?php
include('supermicro.php');
$supermicro = new Supermicro();
echo $supermicro->getLicense("Your BMC MAC Here");
