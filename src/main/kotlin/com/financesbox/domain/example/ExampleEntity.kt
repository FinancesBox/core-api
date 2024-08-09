package com.financesbox.domain.example

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
class ExampleEntity(@Id val id: UUID)